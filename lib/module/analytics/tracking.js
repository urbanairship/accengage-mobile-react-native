// tracking.js
'use strict';
//import { NativeModules } from 'react-native';

//const { RNAccTracking } = NativeModules;

class Tracking {

    constructor() {
        console.log("Tracking module");
    }

    trackEvent(id, parameter) {
        // TODO
    }

    // {id(string):"", label(string):"", category(string):"", price(double):"", quantity(int):""}

    trackPurchase(purchaseId, currency, price, items) {
        // TODO
    }

    trackCart(cartId, item, currency) {
        // TODO
    }

    trackLead(key, value) {
        // TODO
    }
}

var tracking = new Tracking();

exports.tracking = tracking;