// deviceinfo.js
'use strict';
import { NativeModules, Platform } from 'react-native';

const AccStates = NativeModules.RNAccStates;

class States {

    setState(name, value) {
    	if (Platform.OS === 'ios') {
        	AccStates.setState(value, name);
    	} else {
    		AccStates.setState(name, value);
    	}
    }
}

var states = new States();

exports.states = states;