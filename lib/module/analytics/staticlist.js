// staticlist.js
'use strict';
import { NativeModules } from 'react-native';

const AccStaticLists = NativeModules.RNAccStaticLists;

class StaticList {

    // {id(string):"", name(string):"", expireAt(timestamp):"", status(string):""}

    constructor() {
        console.log("StaticList module");
    }

    subscribeToLists(list, date = null) {
        console.log(list);
        console.log(date);
        AccStaticLists.subscribeToLists(list, date);
    }

    unsubscribeFromLists(lists) {
        console.log(lists);
        AccStaticLists.unsubscribeFromLists(lists);
    }

    getSubscriptionStatusForLists(listID) {
        console.log("JS, getting status");
        return AccStaticLists.getSubscriptionStatusForLists(listID);
    }

    getStaticListsSubscribed() {
        return AccStaticLists.getStaticListsSubscribed();
    }
}

var staticList = new StaticList();

exports.staticList = staticList;