
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
        [Accengage setLoggingEnabled:YES];
    }];
}

@end
  
