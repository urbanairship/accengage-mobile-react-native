// push.js
'use strict';

import { NativeModules } from 'react-native';

const { RNAccPush } = NativeModules;

class Push {

    setEnabled(enabled) {
      RNAccPush.setEnabled(enabled);
    }

    isEnabled() {
      RNAccPush.isEnabled();
    }

    setLocked(locked) {
      RNAccPush.setLocked(locked);
    }

    isLocked() {
      RNAccPush.isLocked();
    }

    setToken(token) {
      RNAccPush.setToken(token);
    }

    getToken() {
      return RNAccPush.getToken();
    }
}

var push = new Push();

exports.push = push;