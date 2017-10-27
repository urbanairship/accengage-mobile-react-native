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
        // AccControl.setAllServicesEnabled(value);
    }

    areAllServicesEnabled(callback) {
        // TODO
        // AccControl.areAllServicesEnabled((error, value) => {
        //    callback(Boolean(value));
        //});
    }

    setNetworkCallsEnabled(value) {
        // TODO
        // AccControl.setNetworkCallsEnabled(value);
    }

    areNetworkCallsEnabled(callback) {
        // TODO
        // AccControl.areNetworkCallsEnabled((error, value) => {
        //    callback(Boolean(value));
        // });
    }
}

var control = new Control();

exports.control = control;