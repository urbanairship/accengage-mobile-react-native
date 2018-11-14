//
//  RNAccDeviceInfo.m
//  RNAcc
//
//  Copyright © 2017 Accengage. All rights reserved.
//

#import "RNAccDeviceInfo.h"

@implementation RNAccDeviceInfo

RCT_EXPORT_MODULE()

- (dispatch_queue_t)methodQueue {
    
    return dispatch_get_main_queue();
}

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
            [deviceInfo setString:value forKey:key];
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

@end
