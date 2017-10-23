// staticlist.js
'use strict';

class StaticList {

    // {id(string):"", name(string):"", expireAt(timestamp):"", status(string):""}

    constructor() {
        console.log("StaticList module");
    }

    subscribeFromList(staticLists) {
        // TODO
    }

    unsubscribeFromLists(staticLists) {
        // TODO
    }

    getSubscriptionStatusForLists(staticLists) {
        // TODO
    }

    getListOfSubscriptions() {

    }
}

var staticList = new StaticList();

exports.staticList = staticList;