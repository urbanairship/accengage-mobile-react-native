//
//  RNAcc.m
//  RNAcc
//
//  Copyright © 2017 Accengage. All rights reserved.
//

#import "RNAcc.h"

@implementation RNAcc  {
    bool hasListeners;
}

RCT_EXPORT_MODULE()

- (dispatch_queue_t)methodQueue {
    
    return dispatch_get_main_queue();
}

+ (BOOL)requiresMainQueueSetup {
    
    return false;
}

// Will be called when this module's first listener is added.
- (void)startObserving {
    
    hasListeners = YES;
    // Set up any upstream listeners or background tasks as necessary
}

// Will be called when this module's last listener is removed, or on dealloc.
- (void)stopObserving {
    
    hasListeners = NO;
    // Remove upstream listeners, stop unnecessary background tasks
}

- (NSArray<NSString *> *)supportedEvents {
    
    return @[@"didReceiveNotification", @"didClickNotification"];
}

- (id)init {
    
    self = [super init];
    
    if (self) {
        [Accengage push].delegate = self;
    }
    
    return self;
}

- (void)didReceiveNotificationWithId:(NSString *)notifId parameters:(NSDictionary *)params {
    
    [self sendEventWithName:@"didReceiveNotification" body:@{@"notification": @{
                                                                     @"notificationId" : notifId,
                                                                     @"customParams" : params
                                                                     }}];
}

- (void)didReceiveNotificationWithId:(NSString *)notifId allParameters:(nonnull NSDictionary *)allParams {
    
    [self sendEventWithName:@"didReceiveNotification" body:@{@"notification": @{
                                                                     @"notificationId" : notifId,
                                                                     @"allParams" : allParams
                                                                     }}];
}


- (void)didOpenNotificationWithId:(NSString *)notifId parameters:(NSDictionary *)params {
    
    [self sendEventWithName:@"didClickNotification" body:@{@"notification": @{
                                                                   @"notificationId" : notifId,
                                                                   @"customParams" : params
                                                                   }}];
}

- (void)didOpenNotificationWithId:(NSString *)notifId allParameters:(NSDictionary *)allParams {
    [self sendEventWithName:@"didClickNotification" body:@{@"notification": @{
                                                                   @"notificationId" : notifId,
                                                                   @"allParams" : allParams
                                                                   }}];
}

- (void)didClickOnActionWithIdentifier:(NSString *)actionId forRemoteNotificationWithId:(NSString *)notifId notificationParameters:(NSDictionary *)params1 actionParameters:(NSDictionary *)params2 {
    
    NSMutableDictionary *customParams = @{}.mutableCopy;
    
    [customParams addEntriesFromDictionary:params1];
    [customParams addEntriesFromDictionary:params2];
    [self sendEventWithName:@"didClickNotification" body:@{@"notification": @{
                                                                   @"notificationId" : notifId,
                                                                   @"actionId" : actionId,
                                                                   @"customParams" : customParams
                                                                   }}];
}

- (void)didClickOnActionWithIdentifier:(NSString *)actionId forRemoteNotificationWithId:(NSString *)notifId allNotificationParameters:(NSDictionary *)allParams actionParameters:(NSDictionary *)actionParams {
    
    NSMutableDictionary *params = @{}.mutableCopy;
    
    [params addEntriesFromDictionary:allParams];
    [params addEntriesFromDictionary:actionParams];
    [self sendEventWithName:@"didClickNotification" body:@{@"notification": @{
                                                                   @"notificationId" : notifId,
                                                                   @"actionId" : actionId,
                                                                   @"params" : params
                                                                   }}];
}

@end


