// staticlist.js
'use strict';
import { NativeModules } from 'react-native';

const AccStaticLists = NativeModules.RNAccStaticLists;

class StaticList {

    // {id(string):"", name(string):"", expireAt(timestamp):"", status(string):""}

    constructor() {
        console.log("StaticList module");
    }

    subscribeToLists(lists) {
        console.log(lists);
        AccStaticLists.subscribeToLists(lists);
    }

    unsubscribeFromLists(lists) {
        console.log(lists);
        AccStaticLists.unsubscribeFromLists(lists);
    }

    getSubscriptionStatusForLists(lists) {
        console.log("JS, getting status");
        return AccStaticLists.getSubscriptionStatusForLists(lists);
    }

    getStaticListsSubscribed() {
        return AccStaticLists.getStaticListsSubscribed();
    }
}

var staticList = new StaticList();

exports.staticList = staticList;