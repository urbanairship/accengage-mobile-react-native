// push.js
'use strict';

class Push {
    constructor() {
        console.log("Push module");
    }

    getMsg() {
        return "I'm Push module";
    }
}

var push = new Push();

exports.push = push;