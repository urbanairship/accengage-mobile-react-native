// staticlist.js
'use strict';
import { NativeModules } from 'react-native';

const AccStaticLists = NativeModules.RNAccStaticLists;

class StaticList {

    subscribeToLists(lists) {
        AccStaticLists.subscribeToLists(lists);
    }

    unsubscribeFromLists(lists) {
        AccStaticLists.unsubscribeFromLists(lists);
    }

    getSubscriptionStatusForLists(lists) {
        return AccStaticLists.getSubscriptionStatusForLists(lists);
    }

    getStaticListsSubscribed() {
        return AccStaticLists.getStaticListsSubscribed();
    }
}

var staticList = new StaticList();

exports.staticList = staticList;