//
//  RNAccDeviceTag.m
//  RNAcc
//
//  Copyright © 2018 Accengage. All rights reserved.
//

#import "RNAccDeviceTag.h"

@implementation RNAccDeviceTag

RCT_EXPORT_MODULE()

- (dispatch_queue_t)methodQueue {
    
    return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(setDeviceTag:(NSString*)category identifier:(NSString*)identifier items:(NSDictionary*)items) {
    
    if (!category || !identifier) {
        return;
    }
    
    ACCDeviceTag *deviceTag = [[ACCDeviceTag alloc] initWithCategory:category identifier:identifier];
    
    if (items) {
        [items enumerateKeysAndObjectsUsingBlock:^(id  _Nonnull key, id  _Nonnull obj, BOOL * _Nonnull stop) {
            if ([obj isKindOfClass:[NSString class]]) {
                NSDate *date = [self dateFromString:obj];
                if (date) {
                    [deviceTag setDate:date forKey:key];
                } else {
                    [deviceTag setString:obj forKey:key];
                }
            } else if ([obj isKindOfClass:[NSNumber class]]) {
                [deviceTag setNumber:obj forKey:key];
            } else if ([obj isKindOfClass:[NSDate class]]) {
                [deviceTag setDate:obj forKey:key];
            }
        }];
    }
    
    
    [[Accengage profile] setDeviceTag:deviceTag];
}

RCT_EXPORT_METHOD(deleteDeviceTag:(NSString*)category identifier:(NSString*)identifier) {
    
    if (!category || !identifier) {
        return;
    }
    
    ACCDeviceTag *deviceTag = [[ACCDeviceTag alloc] initWithCategory:category identifier:identifier];
    [[Accengage profile] deleteDeviceTag:deviceTag];
}

#pragma mark - Helper Methods

- (NSDate*)dateFromString:(NSString*)stringParam {
    
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"yyyy'-'MM'-'dd'T'HH':'mm':'ss.SSSZZZ"];
    NSString *finalString = [stringParam stringByReplacingOccurrencesOfString:@"Z" withString:@"-0000"];
    NSDate *date = [dateFormatter dateFromString:finalString];
    
    return date;
}

@end
