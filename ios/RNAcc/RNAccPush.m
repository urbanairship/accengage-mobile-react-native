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

static RNAccPush *sharedMyManager = nil;

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"didReceivedNotification", @"didClickedNotification"];
}

+ (id)sharedManager {
    
    static dispatch_once_t once;
    dispatch_once(&once, ^{
        sharedMyManager = [[self alloc] init];
    });
    return sharedMyManager;
}

- (id)init {
    if (sharedMyManager) {
        return sharedMyManager;
    }
    @synchronized(self) {
        self = [super init];
        if (self) {
            sharedMyManager = self;
        }
        return self;
    }
}

- (void)didReceiveNotificationWithId:(NSString *)notifId parameters:(NSDictionary *)params
{
    [self sendEventWithName:@"didReceivedNotification" body:@{@"notification": @{
                                                                      @"notificationId" : notifId,
                                                                      @"customParams" : params
                                                                      }}];
}

- (void)didOpenNotificationWithId:(NSString *)notifId parameters:(NSDictionary *)params
{
    [self sendEventWithName:@"didClickedNotification" body:@{@"notification": @{
                                                                     @"notificationId" : notifId,
                                                                     @"customParams" : params
                                                                     }}];
}

- (void)didClickOnActionWithIdentifier:(NSString *)actionId forRemoteNotificationWithId:(NSString *)notifId notificationParameters:(NSDictionary *)params1 actionParameters:(NSDictionary *)params2
{
    NSMutableDictionary *customParams = @{}.mutableCopy;
    
    [customParams addEntriesFromDictionary:params1];
    [customParams addEntriesFromDictionary:params2];
    [self sendEventWithName:@"didClickedNotification" body:@{@"notification": @{
                                                                     @"actionId" : notifId,
                                                                     @"customParams" : customParams
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

