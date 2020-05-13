import React, { Component } from "react";
import {
  StyleSheet,
  Text,
  View
} from 'react-native';
import Button from 'react-native-button';
import {Acc} from 'react-native-acc';
import styles from './../../../Styles';

export default class View1Screen extends Component {
  static navigationOptions = ({navigation}) => ({
    title: "View1",
  });

  render() {
    const { navigate } = this.props.navigation;
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>Welcome to View1 !</Text>
        <Button onPress={() => navigate('View2')}
                containerStyle={styles.accbuttoncontainer}
                style={styles.accbutton}>
          View2
        </Button>
      </View>
    );
  }
}
