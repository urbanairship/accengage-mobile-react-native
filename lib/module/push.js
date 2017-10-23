// push.js
'use strict';

//import { NativeModules } from 'react-native';

//const { RNAccPush } = NativeModules;

class Push {
    constructor() {
        console.log("Push module");
    }

    setPushServiceEnabled(value) {
        // TODO
    }

    isPushServiceEnabled() {
        // TODO
    }
}

var push = new Push();

exports.push = push;