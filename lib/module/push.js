// push.js
'use strict';

class Push {
    constructor() {
        console.log("Push module");
    }

    isPushEnabled() {
        // TODO
    }

    setPushEnabled() {
        // TODO
    }

    setPushNotificationLocked() {
        // TODO
    }

    isPushNotificationLocked() {
        // TODO
    }

    getMsg() {
        return "I'm Push module";
    }
}

var push = new Push();

exports.push = push;