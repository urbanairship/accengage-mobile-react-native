//
//  RNAccInApp.m
//  RNAcc
//
//  Created by Bastien Brunaud on 06/11/2017.
//

#import "RNAccInApp.h"

@implementation RNAccInApp
{
    bool hasListeners;
}

RCT_EXPORT_MODULE()

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

// Will be called when this module's first listener is added.
-(void)startObserving {
    hasListeners = YES;
    // Set up any upstream listeners or background tasks as necessary
}

// Will be called when this module's last listener is removed, or on dealloc.
-(void)stopObserving {
    hasListeners = NO;
    // Remove upstream listeners, stop unnecessary background tasks
}

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"onInAppNotifClicked", @"onInAppNotifDidAppear", @"onInAppNotifClosed"];
}

- (id)init {
    self = [super init];
    
    if (self) {
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(onInAppNotifClicked:) name:BMA4SInAppNotification_Clicked object:nil];
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(onInAppNotifDidAppear:) name:BMA4SInAppNotification_DidAppear object:nil];
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(onInAppNotifClosed:) name:BMA4S_InAppNotification_Closed object:nil];
    }
    
    return self;
}

- (void) onInAppNotifClicked:(NSNotification*) notif
{
    if (hasListeners) {
        [self sendEventWithName:@"onInAppNotifClicked" body:@{@"inApp": [self createJavasriptInAppObject:notif]}];
    }
}

- (void) onInAppNotifDidAppear:(NSNotification*) notif
{
    if (hasListeners) {
        [self sendEventWithName:@"onInAppNotifDidAppear" body:@{@"inApp": [self createJavasriptInAppObject:notif]}];
    }
}

- (void) onInAppNotifClosed:(NSNotification*) notif
{
    if (hasListeners) {
        [self sendEventWithName:@"onInAppNotifClosed" body:@{@"inApp": [self createJavasriptInAppObject:notif]}];
    }
}

RCT_EXPORT_METHOD(setInAppDisplayEnabled:(BOOL)enabled)
{
    [BMA4SInAppNotification setNotificationLock:!enabled];
}

RCT_REMAP_METHOD(isInAppDisplayEnabled, isInAppDisplayEnabledWithResolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    resolve([NSNumber numberWithInt:![BMA4SInAppNotification notificationLock]]);
}

// Create readable inApp js object
- (NSDictionary*) createJavasriptInAppObject:(NSNotification*)notif {
    NSDictionary *inAppObject = @{
                                 @"messageId" : @"",
                                 @"displayTemplate" : @"",
                                 @"displayParams" : @{},
                                 @"customParams" : notif.userInfo
                                 };
    return inAppObject;
}

@end
