import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Image,
} from "react-native";
import { useNavigation } from "@react-navigation/native";
import Icon from 'react-native-vector-icons/MaterialCommunityIcons';

export default function Vaccination() {
  const navigation = useNavigation();
  const [item, setitem] = useState([]);
  const [cnt, setCnt] = useState();

  useEffect(() => {
    fetchData();
  }, []);

  const handleBlockPress = (id, data) => {
    navigation.navigate("VaccinationInfoStack", { id, data }); 
  };

  const getDayOfWeek = () => {
    const daysOfWeek = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
    const today = new Date();
    return daysOfWeek[today.getDay()];
  };

  const fetchData = async () => {
    try {
      const response = await fetch(`http://203.252.213.209:8080/api/v1/healthList/vaccination/1`);
      const data = await response.json();
      setitem(data.data.vaccinationItemList); 
      setCnt(data.data.totalCnt);

    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  return (
    <View style={[{ height: "100%" }, { backgroundColor: "white" }]}>
        <View style={styles.screenContainer}>
          <View style={styles.row}>
            {
              cnt === 0 ? (
                <View style={[{ alignItems: "center" }, { paddingTop: 250 }]}> 
                  <Image source={require("../../../assets/images/PatientInfo/ListNonExist.png")} />
                  <Text style={[styles.hospitalName, { paddingTop: 20 }, { paddingBottom: 10 }]}> 확인된 예방 접종 내역이 없습니다. </Text>
                </View>
              ) : <ScrollView style={styles.scrollView}>
                {
                  item.map((item) => {
                    return (
                      <View key={item.vaccId} style={styles.hospitalBlock}>
                      <View style={{ flex: 1 }}>
                        <Text style={styles.timeText}>{item.vaccDate}</Text>
                        <View style={styles.hospitalInfoContainer}>
                          <Image
                            source={require("../../../assets/images/hospital/Hospital.png")}
                            style={styles.hospitalImage}
                          />
                          <View style={[{padding: 5}]}>
                          <Text style={styles.hospitalName}> {item.agencyName} </Text>
                            <Text
                              style={[styles.hospitalInfo, {width: 240}]}
                              numberOfLines={1}
                              ellipsizeMode="tail"
                            > {item.agencyAddress}</Text>
                            <View style={[{flexDirection: "row"}, {paddingTop: 3}]}>
                              <Text style={[styles.timeText, {color: "#94C973"}]}> ㆍ오늘 예약  | </Text>
                              <Text style={[styles.timeText, {color: "#D6D6D6"}]}> {getDayOfWeek()} ({item.agencyTodayOpenTime}~{item.agencyTodayCloseTime})</Text>
                            </View>
                          </View>
                          <TouchableOpacity
                            onPress={() => handleBlockPress(1, item)}
                            activeOpacity={0.7}
                          >
                            <Icon name="chevron-right" size={24} color="#000" />
                          </TouchableOpacity>
                        </View>
                        <View style={styles.vaccinInfo}>
                          <Image 
                            source={require("../../../assets/images/PatientInfo/vaccCert.png")}
                            style={styles.vaccCert}
                          />
                          <Text style={[styles.vaccText, {color: "#94C973"}]}>  {item.vaccSeries}차 {item.vaccName}</Text>
                          <Text style={[styles.vaccText, {color: "#737373"}]}> 접종 완료하셨습니다.</Text>
                        </View>
                      </View>
                    </View>
                        );
                        
                      })
                    }
                  </ScrollView>
            }
          </View>
        </View>
      </View>
    );
  }
  
  const styles = StyleSheet.create({
    screenContainer: {
      flexDirection: "row",
      backgroundColor: "white",
      alignItems: "center",
      justifyContent: "center",
    },
    blocks: {
      width: "49%", // Adjust as needed to fit your design
      height: "40%", // 너비와 높이 비율 유지
      justifyContent: "center",
      alignItems: "center",
      backgroundColor: "#9BD394",
      borderRadius: 6,
      marginTop: 20,
      marginRight: 8,
    },
    one: {
      paddingTop: 40,
      paddingRight: 50,
      paddingLeft: 50,
      backgroundColor: "white",
    },
    row: {
      flexDirection: "row",
      justifyContent: "space-between",
    },
    scrollView: {
      flex: 1,
      width: "100%",
    },
    vaccinInfo: {
      height: 40,
      width: "100%",
      flexDirection: "row",
      justifyContent: "center",
      alignItems: "center",
      backgroundColor: "#EBF2EA",
      borderRadius: 6,
      marginTop: 15,
      marginBottom: 15,
    },
    vaccCert: {
      width: 30,
      height: 30,
    },
    vaccText: {
      fontSize: 16,
    },
    hospitalBlock: {
      flexDirection: "row",
      height: 200,
      backgroundColor: "white",
      padding: 20,
      borderBottomColor: "#D6D6D6",
      borderBottomWidth: 1,
    },
    hospitalImage: {
      width: 80,
      height: 80,
      borderRadius: 5,
      marginTop: 10,
    },
    hospitalInfoContainer: {
      flexDirection: "row",
      justifyContent: "space-between",
    },
    hospitalName: {
      fontSize: 20,
      fontWeight: "bold",
    },
    hospitalInfo: {
      fontSize: 15,
    },
    timeText: {
      fontSize: 14,
    },
    text: {
      fontSize: 17,
    },
  });
  