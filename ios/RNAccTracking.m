#import "RNAccTracking.h"

@implementation RNAccTracking

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(trackEvent:(NSUInteger)eventType parameters:(NSDictionary *) parameters)
{
    if (!parameters) {
        [Accengage trackEvent:eventType];
        return;
    }
    
    NSError *error = nil;
    NSData *data = [NSJSONSerialization dataWithJSONObject:parameters options:0 error:&error];
    
    if (error) {
        NSLog(@"Custom data is sent in unsuported type and ignored");
        [Accengage trackEvent:eventType];
        return;
    }
    
    NSString *jsonString = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    [Accengage trackEvent:eventType withParameters:@[jsonString]];
}

RCT_EXPORT_MODULE()

@end
