// control.js
'use strict';

import { NativeModules, Platform } from 'react-native';

const AccControl = NativeModules.RNAccControl;

class Control {

  setOptinData(isEnabled) {
    AccControl.setOptinData(isEnabled);
  }

  setOptinGeoloc(isEnabled) {
    AccControl.setOptinGeoloc(isEnabled);
  }

  setAllServicesEnabled(isEnabled) {
    if (Platform.OS === 'ios') {
        AccControl.setAllServicesEnabled(isEnabled);
    }
  }

  areAllServicesEnabled() {
    if (Platform.OS === 'ios') {
      return AccControl.areAllServicesEnabled();
    } else {
      return new Promise(function (resolve) {
        resolve(true);
      });
    }
  }

  setNetworkCallsEnabled(isEnabled) {
    if (Platform.OS === 'ios') {
      AccControl.setNetworkCallsEnabled(isEnabled);
    }
  }

  areNetworkCallsEnabled() {
    if (Platform.OS === 'ios') {
      return AccControl.areNetworkCallsEnabled();
    } else {
      return new Promise(function (resolve) {
        resolve(true);
      });
    }
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
      return new Promise(function (resolve) {
        resolve(true);
      });
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
      return new Promise(function (resolve) {
        resolve(true);
      });
    }
  }
}

var control = new Control();

exports.control = control;