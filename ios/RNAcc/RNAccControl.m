//
//  RNAccControl.m
//  RNAcc
//
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNAccControl.h"

@implementation RNAccControl
    
- (dispatch_queue_t)methodQueue {
    
    return dispatch_get_main_queue();
}
    
RCT_EXPORT_MODULE()
    
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

@end
