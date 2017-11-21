//
//  RNAccInApp.h
//  RNAcc
//
//  Created by Bastien Brunaud on 06/11/2017.
//

#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#else
#import "RCTBridgeModule.h"
#import "RCTEventEmitter.h"
#endif

#import <Accengage/Accengage.h>

@interface RNAccInApp : RCTEventEmitter <RCTBridgeModule>

@end
