//
//  RNAccDeviceInfo.m
//  RNAcc
//
//  Copyright © 2017 Facebook. All rights reserved.
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

@end
