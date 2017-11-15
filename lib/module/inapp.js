// inapp.js
'use strict';
import { NativeModules } from 'react-native';

const AccInApp = NativeModules.RNAccInApp;

class InApp {

    setInAppDisplayedCallback(callback) {
      AccInApp.setInAppDisplayedCallback(callback);
    }

    setInAppClickedCallback(callback) {
      AccInApp.setInAppClickedCallback(callback);
    }

    setInAppClosedCallback(callback) {
      AccInApp.setInAppClosedCallback(callback);
    }

    setInAppDisplayEnabled(isEnabled) {
      AccInApp.setInAppDisplayEnabled(isEnabled);
    }

    isInAppDisplayEnabled() {
      return AccInApp.isInAppDisplayEnabled();
    }
}

var inapp = new InApp();

exports.inapp = inapp;