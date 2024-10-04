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

export default function MedicalHistory() {
  const navigation = useNavigation();
  const [item, setitem] = useState([]);
  const [cnt, setCnt] = useState();

  // useEffect(() => {
  //   fetchData();
  // }, []);

  // const handleBlockPress = (id, data) => {
  //   navigation.navigate("HistoryStack", { id, data }); 
  // };

  // const fetchData = async () => {
  //   try {
  //     const response = await fetch(`http://203.252.213.209:8080/api/v1/healthList/vaccination/1`);
  //     const data = await response.json();
  //     setitem(data.data.vaccinationItemList); 
  //     console.log(item);
  //   } catch (error) {
  //     console.error('Error fetching data:', error);
  //   }
  // };
  return (
    <View style={{ height: "100%", backgroundColor: "white" }}>
      <View style={styles.screenContainer}>
        <View style={styles.row}>
          {cnt === 0 ? (
            <View style={{ alignItems: "center", paddingTop: 250 }}>
              <Image source={require("../../assets/images/PatientInfo/ListNonExist.png")} />
              <Text style={[styles.hospitalName, { paddingTop: 20, paddingBottom: 10 }]}>
                완료된 진료 내역이 없어요.
              </Text>
            </View>
          ) : (
            <ScrollView style={styles.scrollView}>
              {item.map((item) => (
                <View key={item.vaccId} style={{ height: "100%", backgroundColor: "white" }}>
                  <View style={styles.screenContainer}>
                    <View style={styles.row}>
                      <ScrollView style={styles.scrollView}>
                        <View style={styles.hospitalBlock}>
                          <View style={{ flex: 1 }}>
                            <Text style={styles.timeText}>2024.06.03 오후 13:00</Text>
                            <View style={styles.hospitalInfoContainer}>
                              <View>
                                <Text style={styles.hospitalName}>김OO 환자</Text>
                                <Text style={[styles.hospitalInfo, { width: 240 }]}>최근 방문 이력 N회</Text>
                              </View>
                              <TouchableOpacity
                                onPress={() => handleBlockPress(1, item)}
                                activeOpacity={0.7}
                              >
                                <Icon name="chevron-right" size={24} color="#000" />
                              </TouchableOpacity>
                            </View>
                            <TouchableOpacity
                              style={styles.prescriptionBlock}
                              activeOpacity={0.7}
                            >
                              <Text style={styles.prescriptionText}>처방전 작성하기</Text>
                            </TouchableOpacity>
                          </View>
                        </View>
                      </ScrollView>
                    </View>
                  </View>
                </View>
              ))}
            </ScrollView>
          )}
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
    height: 180,
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
    paddingTop: 5,
  },
  hospitalInfo: {
    fontSize: 13,
    color: "#A3A3A3",
    paddingTop: 5,
  },
  prescriptionBlock: {
    width: "100%",
    height: 50,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#9BD394",
    borderRadius: 8,
    marginTop: 15,
  },
  prescriptionText: {
    fontSize: 16,
  },
  timeText: {
    fontSize: 14,
  },
  text: {
    fontSize: 17,
  },
});