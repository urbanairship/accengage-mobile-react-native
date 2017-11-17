// control.js
'use strict';

import { NativeModules } from 'react-native';

const AccControl = NativeModules.RNAccControl;

class Control {

    setAllServicesEnabled(isEnabled) {
        AccControl.setAllServicesEnabled(isEnabled);
    }

    areAllServicesEnabled() {
        return AccControl.areAllServicesEnabled();
    }

    setNetworkCallsEnabled(isEnabled) {
        AccControl.setNetworkCallsEnabled(isEnabled);
    }

    areNetworkCallsEnabled() {
        return AccControl.areNetworkCallsEnabled()
    }
}

var control = new Control();

exports.control = control;