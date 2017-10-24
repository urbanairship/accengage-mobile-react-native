//
//  RNAccStaticLists.m
//  RNAcc
//
//  Created by Bastien Brunaud on 24/10/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNAccStaticLists.h"

@implementation RNAccStaticLists

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(subscribeToLists:(NSArray *)lists)
{
    [Accengage subscribeToLists:[self listsToAccLists:lists]];
}

RCT_EXPORT_METHOD(unsubscribeFromLists:(NSArray *)lists)
{
    [Accengage unsubscribeFromLists:[self listsToAccLists:lists]];
}

RCT_EXPORT_METHOD(getListOfSubscriptions:(RCTResponseSenderBlock)callback)
{
    dispatch_async(dispatch_get_main_queue(), ^{
        [Accengage listOfSubscriptions:^(NSArray<ACCList *> * _Nullable result, NSError * _Nullable error) {
            if (!error)
            {
                callback(@[[NSNull null], [self accListsToLists:result]]);
            }
            else
                callback(@[error.localizedDescription, [NSNull null]]);
        }];
    });
}

RCT_EXPORT_METHOD(getSubscriptionStatusForLists:(NSArray *)lists callback:(RCTResponseSenderBlock)callback)
{
    dispatch_async(dispatch_get_main_queue(), ^{
        [Accengage subscriptionStatusForLists:[self listsToAccLists:lists] completionHandler:^(NSArray<ACCList *> * _Nullable result, NSError * _Nullable error) {
            if (!error)
            {
                callback(@[[NSNull null], [self accListsToLists:result]]);
            }
            else
                callback(@[error.localizedDescription, [NSNull null]]);
        }];
    });
}

////////////////////////////////////

#pragma mark -
#pragma mark Helpers

////////////////////////////////////

- (NSMutableArray*)accListsToLists:(NSArray<ACCList*>*)accLists{
    NSMutableArray *lists = [[NSMutableArray alloc] init];
    for (ACCList *accList in accLists)
    {
        [lists addObject:@{@"identifer" : accList.identifier,
                           @"name" : accList.name,
                           @"expirationDate" : [NSNumber numberWithDouble:[accList.expirationDate timeIntervalSince1970]],
                           @"subscriptionStatus" : [self subscriptionStatusToString:accList.subscriptionStatus],
                           }];
    }
    return lists;
}

- (NSMutableArray<ACCList*>*)listsToAccLists:(NSArray*)lists{
    NSMutableArray<ACCList*>* accLists = [[NSMutableArray alloc] init];
    for (NSDictionary* list in lists)
    {
        ACCList *accList = [ACCList alloc];
        if (list[@"expirationDate"])
        {
            NSDate *date = [NSDate dateWithTimeIntervalSince1970:[RCTConvert double:list[@"expirationDate"]]];
            accList = [ACCList listWithId:list[@"identifer"] expirationDate:date];
        }
        else
            accList = [ACCList listWithId:list[@"identifer"]];
        [accLists addObject:accList];
    }
    return accLists;
}

- (NSString*)subscriptionStatusToString:(ACCListSubscriptionStatus)subscriptionStatus{
    NSString *result = nil;
    switch(subscriptionStatus) {
        case ACCListSubscriptionStatusUnknown:
            result = @"Unknow";
            break;
        case ACCListSubscriptionStatusSubscribed:
            result = @"Subscribed";
            break;
        case ACCListSubscriptionStatusUnsubscribed:
            result = @"Unsubscribed";
            break;
        default:
            [NSException raise:NSGenericException format:@"Unexpected subscriptionStatus."];
    }
    return result;
}

@end
