// control.js
'use strict';

class Control {
    constructor() {
        console.log("Push module");
    }

    setRestrictedConnection() {
        // TODO
    }

    isRestrictedConnection() {

    }

    getMsg() {
        return "I'm Push module";
    }
}

var control = new Control();

exports.push = control;