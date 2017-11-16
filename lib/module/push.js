// push.js
'use strict';

import { NativeModules } from 'react-native';

const { RNAccPush } = NativeModules;

class Push {

    setPushServiceEnabled(isEnabled) {
        RNAccPush.setPushServiceEnabled(isEnabled);
    }

    isPushServiceEnabled() {
        return RNAccPush.isPushServiceEnabled();
    }
}

var push = new Push();

exports.push = push;