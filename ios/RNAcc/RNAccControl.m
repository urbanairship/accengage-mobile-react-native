//
//  RNAccControl.m
//  RNAcc
//
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNAccControl.h"

@implementation RNAccControl

RCT_EXPORT_MODULE()

- (dispatch_queue_t)methodQueue {

    return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(setAllServicesEnabled:(BOOL)enabled) {

    [Accengage suspendAllServices:!enabled];
}

RCT_EXPORT_METHOD(areAllServicesEnabled:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {

    resolve([NSNumber numberWithInt:![Accengage isSuspended]]);
}

RCT_EXPORT_METHOD(setNetworkCallsEnabled:(BOOL)enabled) {

    [Accengage setNetworkCallsDisabled:!enabled];
}

RCT_EXPORT_METHOD(areNetworkCallsEnabled:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {

    resolve([NSNumber numberWithInt:![Accengage areNetworkCallsDisabled]]);
}

RCT_EXPORT_METHOD(setGeofenceServiceEnabled:(BOOL)enabled) {

    [BMA4SLocationServices setGeofenceServiceEnabled:enabled];
}

RCT_EXPORT_METHOD(isGeofenceServiceEnabled:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {

    resolve([NSNumber numberWithInt:[BMA4SLocationServices isGeofenceServiceEnabled]]);
}

RCT_EXPORT_METHOD(setBeaconServiceEnabled:(BOOL)enabled) {

    [BMA4SLocationServices setBeaconServiceEnabled:enabled];
}

RCT_EXPORT_METHOD(isBeaconServiceEnabled:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {

    resolve([NSNumber numberWithInt:[BMA4SLocationServices isBeaconServiceEnabled]]);
}

RCT_EXPORT_METHOD(setOptinDataEnabled:(BOOL)enabled) {
    [Accengage setDataOptInEnabled:enabled];
}

RCT_EXPORT_METHOD(setOptinGeolocEnabled:(BOOL)enabled) {
    [Accengage setGeolocOptInEnabled:optinGeoloc];
}

RCT_EXPORT_METHOD(isOptinDataEnabled) {
    resolve([Accengage isDataOptInEnabled]);
}

RCT_EXPORT_METHOD(isOptinGeolocEnabled) {
    resolve([Accengage isGeolocOptInEnabled]);
}

@end
