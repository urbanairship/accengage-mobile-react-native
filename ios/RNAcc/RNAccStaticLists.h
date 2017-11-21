//
//  RNAccStaticLists.h
//  RNAcc
//
//  Copyright © 2017 Facebook. All rights reserved.
//

#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#import <React/RCTConvert.h>
#else
#import "RCTBridgeModule.h"
#import "RCTConvert.h"
#endif

#import <Accengage/Accengage.h>

@interface RNAccStaticLists : NSObject <RCTBridgeModule>

@end
