#import "RNAccPush.h"

@implementation RNAccPush

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(setPushServiceEnabled:(BOOL)enabled)
{
    [Accengage push].suspended = !enabled;
}

RCT_EXPORT_METHOD(isPushServiceEnabled:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    resolve([NSNumber numberWithInt:![[Accengage push] isSuspended]]);
}

@end

