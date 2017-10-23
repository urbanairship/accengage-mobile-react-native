// inapp.js
'use strict';
//import { NativeModules } from 'react-native';

//const { RNAccInApp } = NativeModules;

class InApp {
    constructor() {
        console.log("InApp module");
    }

    setInAppReadyCallback(callback) {
        // TODO
    }

    setInAppInflatedCallback(callback) {
        // TODO
    }

    setInAppDisplayedCallback(callback) {
        // TODO
    }

    setInAppClickedCallback(callback) {
        // TODO
    }

    setInAppClosedCallback(callback) {
        // TODO
    }

    setOverlayPosition() {
        // TODO
    }

    setInAppDisplayEnabled(value) {
        // TODO
    }

    isInAppDisplayEnabled() {
        // TODO
    }
}

var inapp = new InApp();

exports.inapp = inapp;