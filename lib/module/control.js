// control.js
'use strict';

import { NativeModules, Platform } from 'react-native';

const AccControl = NativeModules.RNAccControl;

class Control {

  setAllServicesEnabled(isEnabled) {
    AccControl.setAllServicesEnabled(isEnabled);
  }

  areAllServicesEnabled() {
    return AccControl.areAllServicesEnabled();
  }

  setNetworkCallsEnabled(isEnabled) {
    AccControl.setNetworkCallsEnabled(isEnabled);
  }

  areNetworkCallsEnabled() {
    return AccControl.areNetworkCallsEnabled();
  }

  setGeofenceServiceEnabled(isEnabled) {
    if (Platform.OS === 'ios') {
      AccControl.setGeofenceServiceEnabled(isEnabled);
    }
  }

  isGeofenceServiceEnabled() {
    if (Platform.OS === 'ios') {
      return AccControl.isGeofenceServiceEnabled();
    }
    if (Platform.OS === 'android') {
      return false;
    }
  }

  setBeaconServiceEnabled(isEnabled) {
    if (Platform.OS === 'ios') {
      AccControl.setBeaconServiceEnabled(isEnabled);
    }
  }

  isBeaconServiceEnabled() {
    if (Platform.OS === 'ios') {
      return AccControl.isBeaconServiceEnabled();
    }
    if (Platform.OS === 'android') {
      return false;
    }
  }
}

var control = new Control();

exports.control = control;