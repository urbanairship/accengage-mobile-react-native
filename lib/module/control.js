// control.js
'use strict';

import { NativeModules } from 'react-native';

const AccControl = NativeModules.RNAccControl;

class Control {

    constructor() {
        console.log("Control module");
    }

    setAllServicesEnabled(value) {
        // TODO
    }

    areAllServicesEnabled(callback) {
        // TODO
    }

    setNetworkCallsEnabled(value) {
        // TODO
    }

    areNetworkCallsEnabled(callback) {
        // TODO
    }
}

var control = new Control();

exports.control = control;