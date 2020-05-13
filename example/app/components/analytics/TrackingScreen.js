import React, { Component } from "react";
import {
  StyleSheet,
  Text,
  View
} from 'react-native';
import Button from 'react-native-button';
import {Acc} from 'react-native-acc';
import styles from './../../../Styles';

function trackEvent() {
  var nikita = {
    name: "Nikita",
    age: 1,
    birthDate : new Date('December 17, 1995 03:24:00') 
  };
  Acc.trackCustomEvent(5001, nikita);
}

function trackLead() {
  Acc.trackLead("mylabel", "123");
}

function trackCart() {
  var item = {id: "07", label: "Reebook", category: "Shoes", price: 39.99, quantity: 3};
  Acc.trackCart("02", "EUR", item);
}

function trackPurchase() {
  var item1 = {id: "01", label: "Label#1", category: "Cat#1", price: 9.99, quantity: 2};
  var item2 = {id: "02", label: "Label#2", category: "Cat#1", price: 86.78, quantity: 1};
  var item3 = {id: "03", label: "Label#3", category: "Cat#5", price: 40.09, quantity: 1};
  Acc.trackPurchase("03", "EUR", 136.8, [item1, item2, item3]);
}

export default class TrackingScreen extends Component {
  static navigationOptions = ({navigation}) => ({
    title: "Tracking",
  });
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Button onPress={trackEvent}
                containerStyle={styles.accbuttoncontainer}
                style={styles.accbutton}>
          Track Event
        </Button>
        <Button onPress={trackLead}
                containerStyle={styles.accbuttoncontainer}
                style={styles.accbutton}>
          Track Lead
        </Button>
        <Button onPress={trackCart}
                containerStyle={styles.accbuttoncontainer}
                style={styles.accbutton}>
          Track Cart
        </Button>
        <Button onPress={trackPurchase}
                containerStyle={styles.accbuttoncontainer}
                style={styles.accbutton}>
          Track Purchase
        </Button>
      </View>
    );
  }
}
