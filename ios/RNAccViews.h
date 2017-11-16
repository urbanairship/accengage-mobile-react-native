//
//  RNAccViews.h
//  RNAcc
//
//  Copyright © 2017 Facebook. All rights reserved.
//

#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#else
#import "RCTBridgeModule.h"
#endif

#import <Accengage/Accengage.h>

@interface RNAccViews : NSObject <RCTBridgeModule>

@end
