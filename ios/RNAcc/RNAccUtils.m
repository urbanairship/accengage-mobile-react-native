//
//  RNAccUtils.m
//  RNAcc
//
//  Copyright © 2018 Accengage. All rights reserved.
//

#import "RNAccUtils.h"

@implementation RNAccUtils

+ (NSDate*)dateFromString:(NSString*)stringParam {
    
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"yyyy'-'MM'-'dd'T'HH':'mm':'ss.SSSZZZ"];
    NSString *finalString = [stringParam stringByReplacingOccurrencesOfString:@"Z" withString:@"-0000"];
    NSDate *date = [dateFormatter dateFromString:finalString];
    
    return date;
}

@end
