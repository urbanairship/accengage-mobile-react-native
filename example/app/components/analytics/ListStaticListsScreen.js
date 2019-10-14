'use strict';

import React, {Component} from 'react';
import DatePicker from 'react-native-datepicker'
import Moment from 'moment'
import {
    StyleSheet,
    Text,
    TextInput,
    View,
    Slider,
    Switch,
    NativeModules,
    List,
    ListItem,
    FlatList
} from 'react-native';
import { StackNavigator } from 'react-navigation';
import Button from 'react-native-button';
import Acc from 'react-native-acc';
import styles from './../../../Styles';

export default class ListStaticListsScreen extends Component {
  static navigationOptions = ({navigation}) => ({
    title: "Lists Subscribed",
  });

  constructor(props) {
        super(props);
        this.state = {
            data: [],
            name: ''
        };
        this.getData = this.getData.bind(this);        
    }

    render() {
        return (
            <View style={styles.container}>
                <Button containerStyle={styles.accbuttoncontainer}
                    style={styles.accbutton}
                    onPress={this.getData}>
                    Get Subscribed Lists 
                </Button>
                <FlatList
                  data={this.state.data}
                  renderItem={({item}) => <Text style={styles.text}>Nom : {item.name + " | ExternalID : " + item.listID + " | Expiration Date : " + Moment.unix(item.expirationDate).format('YYYY-MM-DD')}</Text>}
                />
            </View>
        );
    }

    getData() {
        this._getStaticListsSubscribed();
    }

    async _getStaticListsSubscribed() {
          try {
            var data = await Acc.analytics.staticlist.getStaticListsSubscribed();
            console.log(data);
            console.log(data[0].name);
            if(data != null) {
                this.setState({data: data,
                    name: data[0].name});
                console.log(this.state.data);
            }

          } catch (e) {
            console.log(e);
          }
    }

}
