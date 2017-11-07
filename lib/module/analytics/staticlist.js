// staticlist.js
'use strict';
import { NativeModules } from 'react-native';

const AccStaticLists = NativeModules.RNAccStaticLists;

class StaticList {

    // {id(string):"", name(string):"", expireAt(timestamp):"", status(string):""}

    constructor() {
        console.log("StaticList module");
    }

    subscribeToLists(list) {
        console.log(list);
        AccStaticLists.subscribeToLists(list);
    }

    unsubscribeFromLists(lists) {
        console.log(lists);
        AccStaticLists.unsubscribeFromLists(lists);
    }

    getSubscriptionStatusForLists(lists, callback) {
        console.log(list);
        AccStaticLists.getSubscriptionStatusForLists
        // AccStaticLists.getSubscriptionStatusForLists(lists, (error, result) => {
        //    callback(error, result);
        // });
    }
    
    getListsOfSubscriptions() {
        AccStaticLists.getListsOfSubscriptions();   
    }
}

var staticList = new StaticList();

exports.staticList = staticList;