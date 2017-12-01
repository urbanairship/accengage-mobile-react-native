//
//  RNAccStaticLists.m
//  RNAcc
//
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNAccStaticLists.h"

@implementation RNAccStaticLists

- (dispatch_queue_t)methodQueue {
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(subscribeToLists:(NSArray *)lists) {
    
    [Accengage subscribeToLists:[self listsToAccLists:lists]];
}

RCT_EXPORT_METHOD(unsubscribeFromLists:(NSArray *)lists) {
    
    [Accengage unsubscribeFromLists:[self listsToAccLists:lists]];
}

RCT_EXPORT_METHOD(getListOfSubscriptions:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject) {
    
    [Accengage listOfSubscriptions:^(NSArray<ACCList *> * _Nullable result, NSError * _Nullable error) {
        if (!error)
        {
            resolve([self accListsToLists:result]);
        }
        else
            reject(@"error", error.localizedDescription, error);
    }];
}

RCT_EXPORT_METHOD(getSubscriptionStatusForLists:(NSArray *)lists
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    
    [Accengage subscriptionStatusForLists:[self listsToAccLists:lists] completionHandler:^(NSArray<ACCList *> * _Nullable result, NSError * _Nullable error) {
        if (!error)
        {
            resolve([self accListsToLists:result]);
        }
        else
            reject(@"error", error.localizedDescription, error);
    }];
}

////////////////////////////////////

#pragma mark - Helpers

////////////////////////////////////

- (NSArray<NSDictionary *> *)accListsToLists:(NSArray<ACCList *> *)accLists {
    
    NSMutableArray *lists = @[].mutableCopy;
    
    for (ACCList *accList in accLists)
    {
        [lists addObject:@{@"listId" : accList.identifier,
                           @"expirationDate" : @([accList.expirationDate timeIntervalSince1970]),
                           @"name" : accList.name,
                           @"status" : [self subscriptionStatusToString:accList.subscriptionStatus],
                           }];
    }
    return lists;
}

- (NSMutableArray<ACCList *> *)listsToAccLists:(NSArray*)lists {
    
    NSMutableArray<ACCList *> *accLists = @[].mutableCopy;
    
    for (NSDictionary *list in lists) {
        if (![list isKindOfClass:[NSDictionary class]]) {
            break;
        }
        
        NSString *listId = [list[@"listId"] isKindOfClass:[NSString class]] ? list[@"listId"] : nil;
        
        if (listId) {
            ACCList *accList = [ACCList listWithId:list[@"listId"]];
            accList.expirationDate = list[@"expirationDate"] ? [NSDate dateWithTimeIntervalSince1970:[RCTConvert double:list[@"expirationDate"]]] : nil;
            [accLists addObject:accList];
        }
    }
    return accLists;
}

- (NSString *)subscriptionStatusToString:(ACCListSubscriptionStatus)subscriptionStatus {
    
    NSString *result = nil;

    switch(subscriptionStatus) {
        case ACCListSubscriptionStatusUnknown:
            result = @"Unknown";
            break;
        case ACCListSubscriptionStatusSubscribed:
            result = @"Subscribed";
            break;
        case ACCListSubscriptionStatusUnsubscribed:
            result = @"Unsubscribed";
            break;
        default:
            result = @"Unknown";
    }
    return result;
}

@end

