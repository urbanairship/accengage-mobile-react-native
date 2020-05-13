//
//  RNAcc.h
//  RNAcc
//
//  Copyright © 2017 Accengage. All rights reserved.
//

#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import <React/RCTConvert.h>
#else
#import "RCTBridgeModule.h"
#import "RCTEventEmitter.h"
#import "RCTConvert.h"
#endif

#import <Accengage/Accengage.h>

@interface RNAcc : RCTEventEmitter <RCTBridgeModule, ACCPushDelegate>

+ (NSDate*)dateFromString:(NSString*)stringParam;

@end
