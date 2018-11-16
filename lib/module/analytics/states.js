// deviceinfo.js
'use strict';
import { NativeModules } from 'react-native';

const AccStates = NativeModules.RNAccStates;

class States {

    setStates(name, value) {
        AccDeviceInfo.setState:value forKey:name;
    }
}

var states = new States();

exports.states = states;