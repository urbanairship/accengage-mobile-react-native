// views.js
'use strict';
import { NativeModules } from 'react-native';

const AccViews = NativeModules.RNAccViews;

class Views {

    setView(viewName) {
    	AccViews.setView(viewName);
    }

    dismissView(viewName) {
        AccViews.dismissView(viewName);
    }
}

var views = new Views();

exports.views = views;