// staticlist.js
'use strict';
import { NativeModules } from 'react-native';

const AccStaticLists = NativeModules.RNAccStaticLists;

class StaticList {

    // {id(string):"", name(string):"", expireAt(timestamp):"", status(string):""}

    constructor() {
        console.log("StaticList module");
    }

    subscribeToLists(listIDs, date = null) {
        console.log(listIDs);
        console.log(date);
        AccStaticLists.subscribeToLists(listIDs, date);
    }

    unsubscribeFromLists(listIDs) {
        console.log(listIDs);
        AccStaticLists.unsubscribeFromLists(listIDs);
    }

    getSubscriptionStatusForLists(listIDs) {
        console.log("JS, getting status");
        return AccStaticLists.getSubscriptionStatusForLists(listIDs);
    }

    getStaticListsSubscribed() {
        return AccStaticLists.getStaticListsSubscribed();
    }
}

var staticList = new StaticList();

exports.staticList = staticList;