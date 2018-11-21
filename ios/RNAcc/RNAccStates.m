//
//  RNAccStates.m
//  RNAcc
//
//  Copyright © 2018 Accengage. All rights reserved.
//

#import "RNAccStates.h"

@implementation RNAccStates

RCT_EXPORT_MODULE()

- (dispatch_queue_t)methodQueue {
    
    return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(setState:(NSString *)value forKey:(NSString *)name) {
    
    if (!name) {
        return;
    }

    [Accengage setState:value forKey:name];
}

@end

