//
//  RNAcc.m
//  RNAcc
//
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNAcc.h"

@implementation RNAcc

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(start)
{
    [[NSOperationQueue mainQueue] addOperationWithBlock:^{
        [Accengage start];
        [Accengage push].delegate = [RNAccPush sharedManager];
    }];
}

@end
  
