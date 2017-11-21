//
//  RNAccPush.m
//  RNAcc
//
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNAccPush.h"

@implementation RNAccPush

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"onInAppNotifClicked", @"onInAppNotifDidAppear", @"onInAppNotifClosed"];
}

- (id)init {
    self = [super init];
    
    if (self) {
        [Accengage push].delegate = self;
    }
    
    return self;
}

- (void)didReceiveNotificationWithId:(NSString *)notifId parameters:(NSDictionary *)params
{
    [self sendEventWithName:@"didReceiveNotification" body:@{@"notification": @{
                                                                     @"notificationId" : notifId,
                                                                     @"customParams" : params
                                                                     }}];
}

- (void)didOpenNotificationWithId:(NSString *)notifId parameters:(NSDictionary *)params
{
    [self sendEventWithName:@"didOpenNotification" body:@{@"notification": @{
                                                                  @"notificationId" : notifId,
                                                                  @"customParams" : params
                                                                  }}];
}

- (void)didClickOnActionWithIdentifier:(NSString *)actionId forRemoteNotificationWithId:(NSString *)notifId notificationParameters:(NSDictionary *)params1 actionParameters:(NSDictionary *)params2
{
    [self sendEventWithName:@"onInAppNotifClicked" body:@{@"notification": @{
                                                                  @"actionId" : notifId,
                                                                  @"actionParams" : params2,
                                                                  @"customParams" : params1
                                                                  }}];
}

RCT_EXPORT_METHOD(setEnabled:(BOOL)enabled)
{
    if (enabled) {
        ACCNotificationOptions options = (ACCNotificationOptionSound|ACCNotificationOptionBadge|ACCNotificationOptionAlert|ACCNotificationOptionCarPlay);
        [[Accengage push] registerForUserNotificationsWithOptions:options];
    } else {
        NSLog(@"RNAccPush : setEnable to True to register ios Push.");
    }
}

RCT_EXPORT_METHOD(isEnabled:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    resolve([NSNumber numberWithInt:[[UIApplication sharedApplication] isRegisteredForRemoteNotifications]]);
}

RCT_EXPORT_METHOD(setLocked:(BOOL)enabled)
{
    [Accengage push].suspended = !enabled;
}

RCT_EXPORT_METHOD(isLocked:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    resolve([NSNumber numberWithInt:![[Accengage push] isSuspended]]);
}

RCT_EXPORT_METHOD(getToken:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    resolve([[Accengage push] deviceToken]);
}

@end

