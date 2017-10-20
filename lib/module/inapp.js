// inapp.js
'use strict';

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

    setInAppDisplayLocked() {
        // TODO
    }

    isInAppDisplayLocked() {
        // TODO
    }

    getMsg() {
        return "I'm InApp module";
    }
}

var inapp = new InApp();

exports.inapp = inapp;