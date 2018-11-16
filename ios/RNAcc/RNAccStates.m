//
//  RNAccStates.m
//  RNAcc
//
//  Created by Mouna KHEMIRI on 11/16/18.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import "RNAccStates.h"

@implementation RNAccStates

RCT_EXPORT_MODULE()

- (dispatch_queue_t)methodQueue {
    
    return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(setState:(NSString *)value forKey:(NSString *)name) {
    
    if (!name || !value) {
        return;
    }

    [Accengage setState:value forKey:name];
}

@end

