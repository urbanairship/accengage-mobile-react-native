
#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif

#import <Accengage/Accengage.h>

@interface RNAccTracking : NSObject <RCTBridgeModule>

@end
