// push.js
'use strict';

import { NativeModules } from 'react-native';

const { RNAccPush } = NativeModules;

class Push {

    setEnabled(isEnabled) {
        RNAccPush.setEnabled(isEnabled);
    }

    isEnabled() {
        return RNAccPush.isEnabled();
    }


    setLocked(isEnabled) {
        RNAccPush.setLocked(isEnabled);
    }

    isLocked() {
        return RNAccPush.isLocked();
    }

    getToken() {
        return RNAccPush.getToken();
    }
}

var push = new Push();

exports.push = push;