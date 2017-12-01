//
//  RNAccViews.m
//  RNAcc
//
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNAccViews.h"

@implementation RNAccViews

- (dispatch_queue_t)methodQueue {
    
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(setView:(NSString *)view) {
    
    [Accengage trackScreenDisplay:view];
}

RCT_EXPORT_METHOD(dismissView:(NSString *)view) {
    
    [Accengage trackScreenDismiss:view];
}

@end
