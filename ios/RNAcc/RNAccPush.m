//
//  RNAccPush.m
//  RNAcc
//
//  Copyright © 2017 Accengage. All rights reserved.
//

#import "RNAccPush.h"

@implementation RNAccPush

RCT_EXPORT_MODULE(RNAcc)

- (dispatch_queue_t)methodQueue {
    
    return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(setEnabled:(BOOL)enabled) {
    
    if (enabled) {
        ACCNotificationOptions options = (ACCNotificationOptionSound|ACCNotificationOptionBadge|ACCNotificationOptionAlert|ACCNotificationOptionCarPlay);
        [[Accengage push] registerForUserNotificationsWithOptions:options];
    } else {
        NSLog(@"RNAccPush : setEnable to True to register ios Push.");
    }
}

RCT_EXPORT_METHOD(isEnabled:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    
    resolve([NSNumber numberWithInt:[[UIApplication sharedApplication] isRegisteredForRemoteNotifications]]);
}

RCT_EXPORT_METHOD(setProvisionalEnabled:(BOOL)enabled) {
    
    if (enabled) {
        ACCNotificationOptions options = (ACCNotificationOptionSound|ACCNotificationOptionBadge|ACCNotificationOptionAlert|ACCNotificationOptionCarPlay|ACCAuthorizationOptionProvidesAppNotificationSettings|ACCNotificationOptionProvisional);
        [[Accengage push] registerForUserNotificationsWithOptions:options];
    } else {
        NSLog(@"RNAccPush : setProvisionalEnable to True to set provisional optin.");
    }
}

RCT_EXPORT_METHOD(setLocked:(BOOL)enabled) {
    
    [Accengage push].suspended = enabled;
}

RCT_EXPORT_METHOD(isLocked:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    
    resolve([NSNumber numberWithInt:![[Accengage push] isSuspended]]);
}

RCT_EXPORT_METHOD(getToken:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    
    resolve([[Accengage push] deviceToken]);
}

RCT_EXPORT_METHOD(setCustomCategories:(NSDictionary *)customCategories) {
    
    NSMutableSet *accengageSet = [NSMutableSet set];;
    
    for(NSString *key in customCategories) {
        NSArray *dictValue = [customCategories objectForKey:key];
        NSLog(@"key=%@ value=%@", key, [customCategories objectForKey:key]);
        NSMutableArray* actionsObjects = @[].mutableCopy;
        
        for (NSDictionary *action in dictValue) {
            [actionsObjects addObject:[self categoryAction:action[@"id"] title:action[@"title"] foreground:[action[@"foreground"] boolValue]]];
        }
        
        [accengageSet addObject:[self categoryWithIdentifier:key actions:actionsObjects options:UNNotificationCategoryOptionCustomDismissAction]];
    }
    
    [Accengage push].customCategories = accengageSet;
}

#pragma mark - Helper methods

- (id)categoryAction:(NSString *)identifier title:(NSString *)title foreground:(BOOL)foreground {
    
    if ([[NSProcessInfo processInfo] isOperatingSystemAtLeastVersion:(NSOperatingSystemVersion){10, 0, 0}]) {
        
        return [UNNotificationAction actionWithIdentifier:identifier
                                                    title:title
                                                  options:foreground ? UNNotificationActionOptionForeground : UNNotificationActionOptionNone];
    }
    
    UIMutableUserNotificationAction *action = [[UIMutableUserNotificationAction alloc] init];
    action.activationMode = foreground ? UIUserNotificationActivationModeForeground : UIUserNotificationActivationModeBackground;
    action.title = title;
    action.identifier = identifier;
    action.destructive = NO;
    action.authenticationRequired = NO;
    
    return action;
}

- (id)categoryWithIdentifier:(NSString *)identifier actions:(NSArray *)actions options:(UNNotificationCategoryOptions)options {
    
    if ([[NSProcessInfo processInfo] isOperatingSystemAtLeastVersion:(NSOperatingSystemVersion){10, 0, 0}]) {
        return [UNNotificationCategory categoryWithIdentifier:identifier actions:actions intentIdentifiers:@[] options:options];;
    }
    
    UIMutableUserNotificationCategory *category = [[UIMutableUserNotificationCategory alloc] init];
    category.identifier = identifier;
    [category setActions:actions
              forContext:UIUserNotificationActionContextDefault];
    
    return category;
}

@end

