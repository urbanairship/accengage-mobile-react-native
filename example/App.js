/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */


import React, { Component } from 'react';
import { PermissionsAndroid, Platform, Linking } from 'react-native';
import {
  Text,
  View,
  DeviceEventEmitter,
  YellowBox
} from 'react-native';
import { createAppContainer } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';
import Button from 'react-native-button';
import { Acc } from 'react-native-acc';
import styles from './Styles';
import PushScreen from "./app/components/PushScreen";
import AnalyticsScreen from "./app/components/AnalyticsScreen";
import TrackingScreen from './app/components/analytics/TrackingScreen'
import StaticListsScreen from "./app/components/analytics/StaticListsScreen";
import DeviceInfoScreen from "./app/components/analytics/DeviceInfoScreen";
import DeviceTagScreen from "./app/components/analytics/DeviceTagScreen";
import StatesScreen from "./app/components/analytics/StatesScreen";
import InAppScreen from "./app/components/InAppScreen";
import ListStaticListsScreen from "./app/components/analytics/ListStaticListsScreen";
import View1Screen from "./app/components/analytics/View1Screen";
import View2Screen from "./app/components/analytics/View2Screen";
import PushEventsScreen from "./app/components/push/PushEventsScreen.js";
import InAppEventsScreen from "./app/components/inapp/InAppEventsScreen.js";
import ControlScreen from "./app/components/ControlScreen";
import 'core-js/es6/symbol';
import 'core-js/fn/symbol/iterator';
// symbol polyfills
global.Symbol = require('core-js/es6/symbol');
require('core-js/fn/symbol/iterator');

// collection fn polyfills
require('core-js/fn/map');
require('core-js/fn/set');
require('core-js/fn/array/find');

class HomeScreen extends Component {
  static navigationOptions = {
    title: 'Accengage Demo',
  };

  componentDidMount() {
    //We get the deeplink URL
    console.log("Component did Mount !");
      if (Platform.OS === 'android') {
        Linking.getInitialURL().then(url => {
          console.log("url :" + url);
          this.navigate(url);
        });
        //iOS deeplinking (not tested)
      } else {
        Linking.addEventListener('url', this.handleOpenURL);
      }


    }

    componentWillUnmount() {
    Linking.removeEventListener('url', this.handleOpenURL);
  }

  handleOpenURL = (event) => {
    this.navigate(event.url);
  }

    //Decomposing the deeplink and then navigate
   navigate = (url) => {
    const { navigate } = this.props.navigation;
    console.log("url : " + url);
    //Getting url rna4s://control
    const route = url.replace(/.*?:\/\//g, '');
    console.log("route : " + route);
    //If route is control, then go to Control page
    if (route === 'control') {
      navigate('Control')
    };
  }

  constructor() {
    super();

if (Platform.OS === 'android') {
  if (typeof Symbol === 'undefined') {
    if (Array.prototype['@@iterator'] === undefined) {
      Array.prototype['@@iterator'] = function() {
        let i = 0;
        return {
          next: () => ({
            done: i >= this.length,
            value: this[i++],
          }),
        };
      };
    }
  }
}

    DeviceEventEmitter.addListener('com.ad4screen.sdk.intent.category.PUSH_NOTIFICATIONS', function(event) {
      console.log("ActionsReceiver " + JSON.stringify(event));
    });

    if (Platform.OS === 'ios') {
      var customCategories = {"test_react":[
      {id:"1", title:"je participe", foreground:true},
      {id:"2", title:"fermer",foreground:true}]};
      Acc.setCustomCategories(customCategories);
    }


    if (Platform.OS === 'ios') {
      Acc.setOptinDataEnabled(true);
      Acc.setProvisionalEnabled(true);
    }

    if (Platform.OS === 'android') {
      requestLocationPermission().then();
    }


    console.log(Acc);
    Acc.setInAppLocked(false);
    Acc.setPushEnabled(false);

  }

  render() {
    const { navigate } = this.props.navigation;
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Accengage React Native Demo
        </Text>
        <Button onPress={() => navigate('Push')}
                containerStyle={styles.accbuttoncontainer}
                style={styles.accbutton}>
          Push controls
        </Button>
        <Button onPress={() => navigate('InApp')}
                containerStyle={styles.accbuttoncontainer}
                style={styles.accbutton}>
          InApp messages
        </Button>
        <Button onPress={() => navigate('Analytics')}
          containerStyle={styles.accbuttoncontainer}
          style={styles.accbutton}>
          Analytics
        </Button>
        <Button onPress={() => navigate('Control')}
                containerStyle={styles.accbuttoncontainer}
                style={styles.accbutton}>
          Control
        </Button>
      </View>
    );
  }
}

const AccDemoApp = createStackNavigator({
  Home: { screen: HomeScreen },
  Push: {screen: PushScreen},
  InApp: {screen: InAppScreen},
  Analytics: {screen: AnalyticsScreen},
  Tracking: {screen: TrackingScreen},
  StaticLists: {screen: StaticListsScreen},
  ListStaticListsScreen: {screen: ListStaticListsScreen},
  DeviceInfo: {screen: DeviceInfoScreen},
  DeviceTag: {screen: DeviceTagScreen},
  States: {screen: StatesScreen},
  View1: {screen: View1Screen},
  View2: {screen: View2Screen},
  PushEvents: {screen: PushEventsScreen},
  InAppEvents: {screen: InAppEventsScreen},
  Control: {screen: ControlScreen},
});

function getCurrentRouteName(navigationState) {
    if (!navigationState) {
        return null;
    }
    const route = navigationState.routes[navigationState.index];
    // dive into nested navigators
    if (route.routes) {
        return getCurrentRouteName(route);
    }
    return route.routeName;
}

async function requestLocationPermission() {
  try {
    const granted = await PermissionsAndroid.request(
      PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
      {
        'title': 'Location Permission',
        'message': 'MyApp needs access for precise location to work with geofences and beacons'
      }
    )
    if (granted === PermissionsAndroid.RESULTS.GRANTED) {
      console.log("ACCESS_FINE_LOCATION is granted")
    } else {
      console.log("ACCESS_FINE_LOCATION is denied")
    }
  } catch (err) {
    console.log("ACCESS_FINE_LOCATION err: " + err);
    console.warn(err);
  }
}


export default createAppContainer(AccDemoApp); 
