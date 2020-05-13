import React, { Component } from "react";
import {
  StyleSheet,
  Text,
  View
} from 'react-native';
import Button from 'react-native-button';
import styles from './../../Styles';

export default class AnalyticsScreen extends Component {
  static navigationOptions = ({navigation}) => ({
    title: "Analytics",
  });
  render() {
    const { navigate } = this.props.navigation;
    return (
      <View style={styles.container}>
        <Button onPress={() => navigate('Tracking')}
                containerStyle={styles.accbuttoncontainer}
                style={styles.accbutton}>
          Tracking
        </Button>
        <Button onPress={() => navigate('StaticLists')}
                containerStyle={styles.accbuttoncontainer}
                style={styles.accbutton}>
          Static Lists
        </Button>
        <Button onPress={() => navigate('DeviceInfo')}
                containerStyle={styles.accbuttoncontainer}
                style={styles.accbutton}>
          Device Information
        </Button>
        <Button onPress={() => navigate('DeviceTag')}
                containerStyle={styles.accbuttoncontainer}
                style={styles.accbutton}>
          Device Tag
        </Button>
         <Button onPress={() => navigate('States')}
                containerStyle={styles.accbuttoncontainer}
                style={styles.accbutton}>
          States
        </Button>
        <Button onPress={() => navigate('View1')}
                containerStyle={styles.accbuttoncontainer}
                style={styles.accbutton}>
          View1
        </Button>
      </View>
    );
  }
}