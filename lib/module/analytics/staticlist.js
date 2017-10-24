// staticlist.js
'use strict';
import { NativeModules } from 'react-native';

const AccStaticLists = NativeModules.RNAccStaticLists;

class StaticList {

    // {id(string):"", name(string):"", expireAt(timestamp):"", status(string):""}

    constructor() {
        console.log("StaticList module");
    }

    subscribeFromLists(lists) {
        // TODO
        // AccStaticLists.subscribeFromList(lists);
    }

    unsubscribeFromLists(lists) {
        // TODO
        // AccStaticLists.unsubscribeFromLists(lists);
    }

    getSubscriptionStatusForLists(lists, callback) {
        // TODO
/*        AccStaticLists.getSubscriptionStatusForLists(lists, (error, result) => {
            callback(error, result);
        });*/
    }

    getListOfSubscriptions(callback) {
        // TODO
/*        AccStaticLists.getListOfSubscriptions((error, result) => {
            callback(error, result);
        });*/
    }
}

var staticList = new StaticList();

exports.staticList = staticList;