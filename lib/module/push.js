// push.js
'use strict';

import { NativeModules, Platform } from 'react-native';

const { RNAccPush } = NativeModules;

class Push {

  setEnabled(isEnabled) {
    RNAccPush.setEnabled(isEnabled);
  }

  setCustomCategories(customCategories) {
    RNAccPush.setCustomCategories(customCategories);
  }

  isEnabled() {
    return RNAccPush.isEnabled();
  }

  setProvisionalEnabled(isEnabled) {
    RNAccPush.setProvisionalEnabled(isEnabled);
  }

  setLocked(isEnabled) {
    RNAccPush.setLocked(isEnabled);
  }

  isLocked() {
    return RNAccPush.isLocked();
  }

  setToken(token) {
    if (Platform.OS === 'android') {
      RNAccPush.setToken(token);
    }
  }

  getToken() {
    return RNAccPush.getToken();
  }
}

var push = new Push();

exports.push = push;
