// tracking.js
'use strict';
import { NativeModules } from 'react-native';

const AccTracking = NativeModules.RNAccTracking;

class Tracking {

    constructor() {
        console.log("Tracking module");
    }

    trackEvent(id) {
        AccTracking.trackEvent(id);
    }

    trackEvent(id, parameters) {
        AccTracking.trackEvent(id, parameters);
    }

    trackLead(key, value) {
        AccTracking.trackLead(key, value);
    }

    trackCart(cartId, item) {
        AccTracking.trackCart(cartId, item);
    }

    trackPurchase(purchaseId, currency, price, items) {
        AccTracking.trackPurchase(purchaseId, currency, price, items);
    }

}

var tracking = new Tracking();

exports.tracking = tracking;