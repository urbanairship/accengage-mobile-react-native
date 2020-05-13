'use strict';

import React, {Component} from 'react';
import DatePicker from 'react-native-datepicker'
import Moment from 'moment';
import {
    StyleSheet,
    Text,
    TextInput,
    View,
    Slider,
    Switch,
    NativeModules
} from 'react-native';
import Button from 'react-native-button';
import {Acc} from 'react-native-acc';
import styles from './../../../Styles';

export default class StaticListsScreen extends Component {
  static navigationOptions = ({navigation}) => ({
    title: "Static Lists",
  });

  constructor(props) {
        super(props);
        this.state = {
            index: 0,
            idString: null,
            choice: "Subscribe",
            switchIsOn: false,
            resultIdentifier: null,
            resultExpirationDate: null,
            resultName: null,
            resultStatus: null,
        };
        this._sendAction = this._sendAction.bind(this);
  }
  render() {
    const { navigate } = this.props.navigation;
        return (
            <View style={styles.container}>
                <Text style={styles.text}>
                    {this.state.choice}
                </Text>
                <Slider
                    style={{width: 300}}
                    step={1}
                    minimumValue={0}
                    maximumValue={2}
                    value={this.state.index}
                    onValueChange={this._onIndexChanged}
                />
                <View style={styles.flowRight}>
                    <Text style={styles.text}>
                        ExternalID
                    </Text>
                    <TextInput
                        style={styles.input}
                        value={this.state.idString}
                        onChange={this._onIdTextChanged}
                        placeholder='listID#1'/>
                </View>
                <View style={styles.flowRight}>
                    <DatePicker
                        style={{width: 200}}
                        date={this.state.date}
                        mode="date"
                        placeholder="select date"
                        format="YYYY-MM-DD"
                        confirmBtnText="Confirmer"
                        cancelBtnText="Annuler"
                        customStyles={{
                            dateIcon: {
                                position: 'absolute',
                                left: 0,
                                top: 4,
                                marginLeft: 0
                            },
                            dateInput: {
                                marginLeft: 36
                            }
                        }}
                        onDateChange={(date) => {
                            this.setState({date: date})
                        }}
                    />
                    <Switch
                        onValueChange={(value) => this.setState({switchIsOn: value})}
                        style={styles.switch}
                        value={this.state.switchIsOn}/>
                    <Text style={styles.text}>
                        Activer {"\n"} Date d'expiration
                    </Text>
                </View>
                <Button containerStyle={styles.accbuttoncontainer}
                    style={styles.accbutton}
                    onPress={this._sendAction}>
                    Send
                </Button>
                <View style={styles.container}>
                    <Text style={styles.text}>
                        Id: {this.state.resultIdentifier}
                    </Text>
                    <Text style={styles.text}>
                        Name: {this.state.resultName}
                    </Text>
                    <Text style={styles.text}>
                        Status: {this.state.resultStatus}
                    </Text>
                    <Text style={styles.text}>
                        Expiration Date: {this.state.resultExpirationDate}
                    </Text>
                </View>
                <Button containerStyle={styles.accbuttoncontainer}
                    style={styles.accbutton}
                    onPress={() => navigate('ListStaticListsScreen')}>
                    List
                </Button>
            </View>
        );
    }

    _onIdTextChanged = (event) => {
        this.setState({idString: event.nativeEvent.text});
    };

    _onIndexChanged = (value) => {
        if (value === 0)
            this.setState({choice: "Subscribe"});
        else if (value === 1)
            this.setState({choice: "Unsubscribe"});
        else if (value === 2)
            this.setState({choice: "Get status"});
        this.setState({index: value});
    };

    async _getSubscriptionStatusForList() {
      try {
        var lists = [];
        var list = {listId: this.state.idString};
        lists.push(list);
        var result = await Acc.getSubscriptionStatusForLists(lists);
        this.setState({
                        resultIdentifier: result[0].listId,
                        resultName: result[0].name,
                        resultStatus: result[0].status,
                        resultExpirationDate: Moment.unix(result[0].expirationDate).format('YYYY-MM-DD')
                    }); 
      } catch (e) {
        console.log(e);
      }
    }

    _sendAction = () => {
      var lists = [];
        if (this.state.index === 0) {
            if (this.state.switchIsOn) {
                var list = {listId: this.state.idString, expirationDate: Moment(this.state.date, 'YYYY-MM-DD').unix()};
                lists.push(list);
                Acc.subscribeToLists(lists);
            }
            else {
                var list = {listId: this.state.idString};
                lists.push(list);
                Acc.subscribeToLists(lists);
            }
        }
        else if (this.state.index === 1) {
            var list = {listId: this.state.idString};
            lists.push(list);
            Acc.unsubscribeFromLists(lists);
        }
        else if (this.state.index === 2) {
            var list = {listId: this.state.idString};
            lists.push(list);
            this._getSubscriptionStatusForList(lists);
        } 
    }
}