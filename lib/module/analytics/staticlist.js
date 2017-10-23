// staticlist.js
'use strict';
//import { NativeModules } from 'react-native';

//const { RNAccStaticLists } = NativeModules;

class StaticList {

    // {id(string):"", name(string):"", expireAt(timestamp):"", status(string):""}

    constructor() {
        console.log("StaticList module");
    }

    subscribeFromList(lists) {
        // TODO
    }

    unsubscribeFromLists(lists) {
        // TODO
    }

    getSubscriptionStatusForLists(result, error) {
        // TODO
    }

    getListOfSubscriptions(result, error) {
        // TODO
    }
}

var staticList = new StaticList();

exports.staticList = staticList;