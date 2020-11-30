:warning: Note that all Accengage repos will move to Airship's github organisation. We set a redirection on the new links so you don't have to change anything.
If you notice something wrong, do hesitate to contact our customer support team.
This repo will be found now at: https://github.com/urbanairship/accengage-mobile-react-native

# react-native-acc
React-native module wrapping [Android](https://documentation.accengage.com/sdk/android/4.1/) and [iOS](https://documentation.accengage.com/sdk/ios/7.1/) Accengage native SDKs.

### Features
<u>For Android</u>

* InApp (without geofencing and beacons)
* Scheduled Alarms (without geofencing and beacons)
* Analytics (Events, Views, Device Info, Static Lists, Subscription tag, States)

If you want to use ***push notifications for FCM***, ***geofencing*** and ***beacons***, please add additional corresponding accengage react-native modules: [react-native-fcm](https://github.com/urbanairship/accengage-react-native-acc-fcm), [react-native-geofences](https://github.com/urbanairship/accengage-mobile-react-geofences), [react-native-beacons](https://github.com/urbanairship/accengage-mobile-react-beacons). The fact that Accengage Android native SDK has plugins allowed us to separate the functionality on different modules and do not oblige you to use, for instance, beacons and geofencing by default.

<u>For iOS</u>

* InApp
* Scheduled Alarms
* Analytics (Events, Views, Device Info, Static Lists, Subscription tag, States)
* Push Notifications

Checkout our [react-native sample](https://github.com/urbanairship/accengage-mobile-react-native-demo) using these features. 

## Installation
```
npm install react-native-acc
react-native link
```

## Integration

### Android
TODO

### iOS
TODO

## Usage
### Controls
### Push Notification
### InApps
### Analytics
#### Events
#### Views
#### Device Info
#### Static Lists
#### Subscription tag
#### States
