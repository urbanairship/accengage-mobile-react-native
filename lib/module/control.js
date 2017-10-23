// control.js
'use strict';

//import { NativeModules } from 'react-native';

//const { RNAccControl } = NativeModules;

class Control {

    constructor() {
        console.log("Control module");
    }

    setAllServicesEnabled(value) {
        // TODO
    }

    areAllServicesEnabled() {
        // TODO
    }

    setNetworkCallsEnabled(value) {
        // TODO
    }

    areNetworkCallsEnabled() {
        // TODO
    }
}

var control = new Control();

exports.control = control;