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

    // {id(string):"", label(string):"", category(string):"", price(double):"", quantity(int):""}

    trackPurchase(purchaseId, currency, items, price) {
        // TODO
        //AccTracking.trackPurchase(purchaseId, currency, items, price);
    }

    trackCart(cartId, currency, item) {
        // TODO
        //AccTracking.trackCart(cartId, currency, item);
    }

    trackLead(key, value) {
        // TODO
        //AccTracking.trackLead(key, value);
    }
}

var tracking = new Tracking();

exports.tracking = tracking;