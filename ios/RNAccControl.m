#import "RNAccControl.h"

@implementation RNAccControl
    
- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
    
RCT_EXPORT_MODULE()
    
RCT_EXPORT_METHOD(setAllServicesEnabled:(BOOL)enabled)
{
    [Accengage suspendAllServices:!enabled];
}
    
RCT_EXPORT_METHOD(areAllServicesEnabled:(RCTResponseSenderBlock)callback)
{
    dispatch_async(dispatch_get_main_queue(), ^{
        callback(@[[NSNull null], @(![Accengage isSuspended])]);
	});
}
                   
RCT_EXPORT_METHOD(setNetworkCallsEnabled:(BOOL)enabled)
{
    [Accengage setNetworkCallsDisabled:!enabled];
}
                   
RCT_EXPORT_METHOD(areNetworkCallsEnabled:(RCTResponseSenderBlock)callback)
{
    dispatch_async(dispatch_get_main_queue(), ^{
        callback(@[[NSNull null], @(![Accengage areNetworkCallsDisabled])]);
    });
}

@end
