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

# pragma mark - RNAccControl

RCT_EXPORT_METHOD(setAllServicesEnabled:(BOOL)enabled) {

    [Accengage suspendAllServices:!enabled];
}

RCT_EXPORT_METHOD(areAllServicesEnabled:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {

    resolve([NSNumber numberWithInt:![Accengage isSuspended]]);
}

RCT_EXPORT_METHOD(setNetworkCallsEnabled:(BOOL)enabled) {

    [Accengage setNetworkCallsDisabled:!enabled];
}

RCT_EXPORT_METHOD(areNetworkCallsEnabled:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {

    resolve([NSNumber numberWithInt:![Accengage areNetworkCallsDisabled]]);
}

RCT_EXPORT_METHOD(setGeofenceServiceEnabled:(BOOL)enabled) {

    [BMA4SLocationServices setGeofenceServiceEnabled:enabled];
}

RCT_EXPORT_METHOD(isGeofenceServiceEnabled:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {

    resolve([NSNumber numberWithInt:[BMA4SLocationServices isGeofenceServiceEnabled]]);
}

RCT_EXPORT_METHOD(setBeaconServiceEnabled:(BOOL)enabled) {

    [BMA4SLocationServices setBeaconServiceEnabled:enabled];
}

RCT_EXPORT_METHOD(isBeaconServiceEnabled:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {

    resolve([NSNumber numberWithInt:[BMA4SLocationServices isBeaconServiceEnabled]]);
}

RCT_EXPORT_METHOD(setOptinDataEnabled:(BOOL)enabled) {
    [Accengage setDataOptInEnabled:enabled];
}

RCT_EXPORT_METHOD(setOptinGeolocEnabled:(BOOL)enabled) {
    [Accengage setGeolocOptInEnabled:enabled];
}

RCT_EXPORT_METHOD(isOptinDataEnabled:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    resolve([NSNumber numberWithInt:[Accengage isDataOptInEnabled]]);
}

RCT_EXPORT_METHOD(isOptinGeolocEnabled:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    resolve([NSNumber numberWithInt:[Accengage isGeolocOptInEnabled]]);
}

# pragma mark - RNAccDeviceInfo

RCT_EXPORT_METHOD(updateDeviceInfo:(NSDictionary*)deviceInfo) {
    
    [Accengage updateDeviceInfo:deviceInfo];
}

RCT_EXPORT_METHOD(updateDeviceInformation:(NSString*)action
                  key:(NSString*)key
                  value:(id)value
                  resolve:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    
    ACCDeviceInformationSet *deviceInfo = [[ACCDeviceInformationSet alloc] init];
    
    if ([action isEqualToString:@"set"]) {
        
        if ([value isKindOfClass:[NSString class]]) {
            NSDate *date = [RNAccUtils dateFromString:value];
            if (date) {
                [deviceInfo setDate:date forKey:key];
            } else {
                [deviceInfo setString:value forKey:key];
            }
        } else if ([value isKindOfClass:[NSNumber class]]) {
            [deviceInfo setNumber:value forKey:key];
        } else if ([value isKindOfClass:[NSDate class]]) {
            [deviceInfo setDate:value forKey:key];
        }
        
    } else if ([action isEqualToString:@"delete"]) {
        
        [deviceInfo deleteValueForKey:key];
        
    } else if ([action isEqualToString:@"increment"]) {
        
        [deviceInfo incrementValueBy:value forKey:key];
        
    } else if ([action isEqualToString:@"decrement"]) {
        
        [deviceInfo decrementValueBy:value forKey:key];
        
    }
    
    [Accengage.profile updateDeviceInformation:deviceInfo withCompletionHandler:^(NSError * _Nullable error) {
        if (!error)
        {
            resolve(@"updateDeviceInformation OK");
        }
        else
            reject(@"error", error.localizedDescription, error);
    }];
}

# pragma mark RNAccDeviceTags

RCT_EXPORT_METHOD(setDeviceTag:(NSString*)category identifier:(NSString*)identifier items:(NSDictionary*)items) {
    
    if (!category || !identifier) {
        return;
    }
    
    ACCDeviceTag *deviceTag = [[ACCDeviceTag alloc] initWithCategory:category identifier:identifier];
    
    if (items) {
        [items enumerateKeysAndObjectsUsingBlock:^(id  _Nonnull key, id  _Nonnull obj, BOOL * _Nonnull stop) {
            if ([obj isKindOfClass:[NSString class]]) {
                NSDate *date = [RNAccUtils dateFromString:obj];
                if (date) {
                    [deviceTag setDate:date forKey:key];
                } else {
                    [deviceTag setString:obj forKey:key];
                }
            } else if ([obj isKindOfClass:[NSNumber class]]) {
                [deviceTag setNumber:obj forKey:key];
            } else if ([obj isKindOfClass:[NSDate class]]) {
                [deviceTag setDate:obj forKey:key];
            }
        }];
    }
    
    
    [[Accengage profile] setDeviceTag:deviceTag];
}

RCT_EXPORT_METHOD(deleteDeviceTag:(NSString*)category identifier:(NSString*)identifier) {
    
    if (!category || !identifier) {
        return;
    }
    
    ACCDeviceTag *deviceTag = [[ACCDeviceTag alloc] initWithCategory:category identifier:identifier];
    [[Accengage profile] deleteDeviceTag:deviceTag];
}

# pragma mark RNAccStates

RCT_EXPORT_METHOD(setState:(NSString *)value forKey:(NSString *)name) {
    
    if (!name) {
        return;
    }

    [Accengage setState:value forKey:name];
}

# pragma mark RNAccInApp

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
    
    return @[@"didInAppClick", @"didInAppDisplay", @"didInAppClose"];
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
        [self sendEventWithName:@"didInAppClick" body:@{@"inApp": [self createJavasriptInAppObject:notif]}];
    }
}

- (void) onInAppNotifDidAppear:(NSNotification*) notif
{
    if (hasListeners) {
        [self sendEventWithName:@"didInAppDisplay" body:@{@"inApp": [self createJavasriptInAppObject:notif]}];
    }
}

- (void) onInAppNotifClosed:(NSNotification *) notif {
    if (hasListeners) {
        [self sendEventWithName:@"didInAppClose" body:@{@"inApp": [self createJavasriptInAppObject:notif]}];
    }
}

RCT_EXPORT_METHOD(setLocked:(BOOL)enabled) {
    
    [BMA4SInAppNotification setNotificationLock:enabled];
}

RCT_REMAP_METHOD(isLocked, isLockedWithResolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    
    resolve([NSNumber numberWithInt:[BMA4SInAppNotification notificationLock]]);
}

// Create readable inApp js object
- (NSDictionary *) createJavasriptInAppObject:(NSNotification *)notif {
    
    NSDictionary *inAppObject = @{
                                  @"messageId" : @"",
                                  @"displayTemplate" : @"",
                                  @"displayParams" : @{},
                                  @"customParams" : notif.userInfo
    };
    return inAppObject;
}

# pragma mark RNAccPush

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

# pragma mark RNAccTracking

RCT_EXPORT_METHOD(trackCart:(NSString *)cartId
                  currency:(NSString *)currency
                  item:(NSDictionary *)cartItem) {
    
    NSString *itemId = [cartItem[@"id"] isKindOfClass:[NSString class]] ? cartItem[@"id"] : nil;
    NSString *itemName = [cartItem[@"name"] isKindOfClass:[NSString class]] ? cartItem[@"name"] : nil;
    NSString *itemBrand = [cartItem[@"brand"] isKindOfClass:[NSString class]] ? cartItem[@"brand"] : nil;
    NSString *itemCategory = [cartItem[@"category"] isKindOfClass:[NSString class]] ? cartItem[@"category"] : nil;
    
    if (itemId) {
        ACCCartItem *item = [ACCCartItem itemWithId:itemId
                                               name:itemName
                                              brand:itemBrand
                                           category:itemCategory
                                              price:[RCTConvert double:cartItem[@"price"]]
                                           quantity:[RCTConvert NSInteger:cartItem[@"quantity"]]];
        
        [Accengage trackCart:cartId currency:currency item:item];
    }
}

RCT_EXPORT_METHOD(trackPurchase:(NSString *)purchaseId
                  currency:(NSString *)currency
                  amount:(nonnull NSNumber *)purchaseAmount
                  items:(nullable NSArray *)purchasedItems) {
    
    NSMutableArray<ACCCartItem *> *result = @[].mutableCopy;
    
    for (NSDictionary *object in purchasedItems) {
        if (![object isKindOfClass:[NSDictionary class]]) {
            break;
        }
        
        NSString *itemId = [object[@"id"] isKindOfClass:[NSString class]] ? object[@"id"] : nil;
        NSString *itemName = [object[@"name"] isKindOfClass:[NSString class]] ? object[@"name"] : nil;
        NSString *itemBrand = [object[@"brand"] isKindOfClass:[NSString class]] ? object[@"brand"] : nil;
        NSString *itemCategory = [object[@"category"] isKindOfClass:[NSString class]] ? object[@"category"] : nil;
        
        if (itemId) {
            ACCCartItem *item = [ACCCartItem itemWithId:itemId
                                                   name:itemName
                                                  brand:itemBrand
                                               category:itemCategory
                                                  price:[RCTConvert double:object[@"price"]]
                                               quantity:[RCTConvert NSInteger:object[@"quantity"]]];
            [result addObject:item];
        }
    }
    
    [Accengage trackPurchase:purchaseId currency:currency items:result.copy amount:purchaseAmount];
}

RCT_EXPORT_METHOD(trackLead:(NSString *)key value:(NSString *)value) {
    
    [Accengage trackLead:key value:value];
}

RCT_EXPORT_METHOD(trackEvent:(NSUInteger)eventType parameters:(NSDictionary *) parameters) {
    
    if (![parameters isKindOfClass:[NSDictionary class]]) {
        [Accengage trackEvent:eventType];
        return;
    }
    
    NSError *error = nil;
    NSData *data = [NSJSONSerialization dataWithJSONObject:parameters options:0 error:&error];
    
    if (error || !data) {
        NSLog(@"Custom data is sent in unsuported type and ignored");
        [Accengage trackEvent:eventType];
        return;
    }
    
    NSString *jsonString = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    [Accengage trackEvent:eventType withParameters:@[jsonString]];
}

RCT_EXPORT_METHOD(trackCustomEvent:(NSUInteger)eventType withCustomParameters:(NSDictionary *) customParameters) {
    
    ACCCustomEventParams *customEventParams = [[ACCCustomEventParams alloc] init];
    [customParameters enumerateKeysAndObjectsUsingBlock:^(id  _Nonnull key, id  _Nonnull obj, BOOL * _Nonnull stop) {
        if ([obj isKindOfClass:[NSString class]]) {
            NSDate *date = [RNAccUtils dateFromString:obj];
            if (date) {
                [customEventParams setDate:date forKey:key];
            } else {
                [customEventParams setString:obj forKey:key];
            }
        } else if ([obj isKindOfClass:[NSNumber class]]) {
            [customEventParams setNumber:obj forKey:key];
        } else if ([obj isKindOfClass:[NSDate class]]) {
            [customEventParams setDate:obj forKey:key];
        }
    }];
    
    [Accengage trackEvent:eventType withCustomParameters:customEventParams];
}

# pragma mark RNAccStaticLists

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

# pragma mark RNaccViews

RCT_EXPORT_METHOD(setView:(NSString *)view) {
    
    [Accengage trackScreenDisplay:view];
}

RCT_EXPORT_METHOD(dismissView:(NSString *)view) {
    
    [Accengage trackScreenDismiss:view];
}

# pragma mark RNAccUtils

+ (NSDate*)dateFromString:(NSString*)stringParam {
    
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"yyyy'-'MM'-'dd'T'HH':'mm':'ss.SSSZZZ"];
    NSString *finalString = [stringParam stringByReplacingOccurrencesOfString:@"Z" withString:@"-0000"];
    NSDate *date = [dateFormatter dateFromString:finalString];
    
    return date;
}

@end


