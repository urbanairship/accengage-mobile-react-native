// inapp.js
'use strict';

class InApp {
    constructor() {
        console.log("InApp module");
    }

    getMsg() {
        return "I'm InApp module";
    }
}

var inapp = new InApp();

exports.inapp = inapp;