// acc.js
'use strict';

var moduleInapp = require('./module/inapp');
var modulePush = require('./module/push');

module.exports = {
    inapp: moduleInapp.inapp,
    push: modulePush.push
}