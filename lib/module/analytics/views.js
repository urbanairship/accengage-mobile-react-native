// views.js
'use strict';
import { NativeModules } from 'react-native';

const AccViews = NativeModules.RNAccViews;

class Views {

    constructor() {
        console.log("Views module");
    }

    setView(viewName) {
    	AccViews.setView(viewName);
    }
}

var views = new Views();

exports.views = views;