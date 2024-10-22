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

export default function Examination({route}) {
  const navigation = useNavigation();
  const { userId } = route.params;
  console.log("userId", userId);
  const [item, setitem] = useState([]);
  const [cnt, setCnt] = useState();

  // const handleBlockPress = ( data ) => {
  //   navigation.navigate("ExaminationInfoStack", { id: 1, data }); // 건강 검진 상세
  // };
  const handleLoad = (userId, data) => {
    navigation.navigate("ExaminationDetails", { userId, data });
  };


  const fetchData = async () => { 
    try {
      const response = await fetch(`http://203.252.213.209:8080/api/v1/healthList/healthScreening/${userId}`);
      const data = await response.json();
      console.log(data);
      setitem(data.data.healthScreeningItemList);
      setCnt(data.data.totalCnt);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <View style={[{ height: "100%" }, { backgroundColor: "white" }]}>
        <View style={styles.screenContainer}>
          <View style={styles.row}>
            {
              cnt === 0 ? (
                <View style={[{ alignItems: "center" }, { paddingTop: 250 }]}>
                  <Image source={require("../../../assets/images/PatientInfo/ListNonExist.png")} />
                  <Text style={[styles.hospitalName, { paddingTop: 20 }, { paddingBottom: 10 }]}> 확인된 건강 검진 내역이 없어요. </Text>
                  <Text> 확인된 건강 검진 내역이 없습니다. </Text>
                </View>
              ) : <ScrollView style={styles.scrollView}>
                {
                  item.map((item) => {
                    return (
                      <View key={item.hsId} style={styles.hospitalBlock}>
                        <View style={{ flex: 1 }}>
                          <Text style={styles.timeText}>{item.diagDate}</Text>
                          <View style={styles.row}>
                            <View>
                              <Text style={styles.hospitalName}>{item.doctorName} 의사</Text>
                              <Text style={styles.hospitalInfo}>{item.doctorHospital}</Text>
                            </View>      
                            <TouchableOpacity
                              style={{ paddingLeft: 110 }}
                              onPress={() => handleLoad(userId, item)}
                              activeOpacity={0.7}
                            >
                              <Icon name="chevron-right" size={24} color="#000" />
                            </TouchableOpacity>
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
    row: {
      flexDirection: "row",
      justifyContent: "space-between",
    },
    scrollView: {
      flex: 1,
      width: "100%",
    },
    hospitalBlock: {
      flexDirection: "row",
      height: 110,
      backgroundColor: "white",
      padding: 20,
      borderBottomColor: "#D6D6D6",
      borderBottomWidth: 1,
    },
    hospitalName: {
      fontSize: 20,
      fontWeight: "bold",
      paddingTop: 5,
      paddingBottom: 5,
    },
    hospitalInfo: {
      fontSize: 14,
      color: "#A3A3A3",
    },
    text: {
      fontSize: 17,
    },
  });
  