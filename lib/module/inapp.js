// inapp.js
'use strict';
import { NativeEventEmitter, NativeModules } from 'react-native';

const { RNAccInApp } = NativeModules;

const InAppManagerEmitter = new NativeEventEmitter(RNAccInApp);

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
        const subscription = InAppManagerEmitter.addListener(
            'onInAppNotifDidAppear',
            (reminder) => {
                callback(reminder.customParams);
                subscription.remove();
            }
        );
    }

    setInAppClickedCallback(callback) {
        const subscription = InAppManagerEmitter.addListener(
            'onInAppNotifClicked',
            (reminder) => {
                callback(reminder.customParams);
                subscription.remove();
            }
        );
    }

    setInAppClosedCallback(callback) {
        const subscription = InAppManagerEmitter.addListener(
            'onInAppNotifClosed',
            (reminder) => {
                callback(reminder.customParams);
                subscription.remove();
            }
        );
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