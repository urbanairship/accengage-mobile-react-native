/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "AppDelegate.h"

#import <React/RCTBridge.h>
#import <React/RCTBundleURLProvider.h>
#import <React/RCTRootView.h>
#import <React/RCTLinkingManager.h>
//#import <Accengage/Accengage.h>

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
//    ACCConfiguration *config = [ACCConfiguration defaultConfig];
//    config.launchOptions = launchOptions;
//    
//    BOOL dataOptin = [[[NSUserDefaults standardUserDefaults] valueForKey:@"dataOptinApp"] boolValue];
//    BOOL geolocOptin = [[[NSUserDefaults standardUserDefaults] valueForKey:@"geolocOptinApp"] boolValue];
//    
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)), dispatch_get_main_queue(),^{
//        [Accengage startWithConfig:config optIn:ACCOptInEnabled];
//        if (dataOptin) {
//            [Accengage setDataOptInEnabled:dataOptin];
//        }
//        if (geolocOptin) {
//            [Accengage setGeolocOptInEnabled:geolocOptin];
//        }
//        [Accengage setLoggingEnabled:YES];
//    });
  
    RCTBridge *bridge = [[RCTBridge alloc] initWithDelegate:self launchOptions:launchOptions];
    RCTRootView *rootView = [[RCTRootView alloc] initWithBridge:bridge
                                                     moduleName:@"RnDiffApp"
                                              initialProperties:nil];
    
    rootView.backgroundColor = [[UIColor alloc] initWithRed:1.0f green:1.0f blue:1.0f alpha:1];
    
    self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
    UIViewController *rootViewController = [UIViewController new];
    rootViewController.view = rootView;
    self.window.rootViewController = rootViewController;
    [self.window makeKeyAndVisible];
    return YES;
}

- (NSURL *)sourceURLForBridge:(RCTBridge *)bridge
{
#if DEBUG
    return [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"example/index" fallbackResource:nil];
#else
    return [[NSBundle mainBundle] URLForResource:@"main" withExtension:@"jsbundle"];
#endif
}

@end
