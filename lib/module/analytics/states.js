// deviceinfo.js
'use strict';
import { NativeModules } from 'react-native';

const AccStates = NativeModules.RNAccStates;

class States {

    setState(name, value) {
        AccStates.setState(value, name);
    }
}

var states = new States();

exports.states = states;