import React, { Component } from "react";
import {
  StyleSheet,
  Text,
  View
} from 'react-native';
import styles from './../../../Styles';

export default class View2Screen extends Component {

  static navigationOptions = ({navigation}) => ({
    title: "View2",
  });

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>Welcome to View2 !</Text>
      </View>
    );
  }


}
