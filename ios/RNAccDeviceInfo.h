//
//  RNAccDeviceInfo.h
//  RNAcc
//
//  Created by Bastien Brunaud on 14/11/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#else
#import "RCTBridgeModule.h"
#endif

#import <Accengage/Accengage.h>

@interface RNAccDeviceInfo : NSObject <RCTBridgeModule>

@end
