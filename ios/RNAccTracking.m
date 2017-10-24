#import "RNAccTracking.h"

@implementation RNAccTracking

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(trackCart:(NSString *)cartId
                  currency:(NSString *)currency
                  item:(NSDictionary *)cartItem)
{
    ACCCartItem *item = [ACCCartItem itemWithId:cartItem[@"id"]
                                           name:cartItem[@"name"]
                                          brand:cartItem[@"brand"]
                                       category:cartItem[@"category"]
                                          price:[RCTConvert double:cartItem[@"price"]]
                                       quantity:[RCTConvert NSInteger:cartItem[@"quantity"]]];
    [Accengage trackCart:cartId currency:currency item:item];
}

RCT_EXPORT_METHOD(trackPurchase:(NSString *)purchaseId
                  currency:(NSString *)currency
                  items:(nullable NSArray *)purchasedItems
                  amount:(nonnull NSNumber *)purchaseAmount)
{
    NSMutableArray* result = [[NSMutableArray alloc] init];
    for (NSDictionary* object in purchasedItems) {
        ACCCartItem *item = [ACCCartItem itemWithId:object[@"id"]
                                               name:object[@"name"]
                                              brand:object[@"brand"]
                                           category:object[@"category"]
                                              price:[RCTConvert double:object[@"price"]]
                                           quantity:[RCTConvert NSInteger:object[@"quantity"]]];
        [result addObject:item];
    }
    [Accengage trackPurchase:purchaseId currency:currency items:result amount:purchaseAmount];
}

RCT_EXPORT_METHOD(trackLead:(NSString *)key value:(NSString *)value)
{
    [Accengage trackLead:key value:value];
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

@end
