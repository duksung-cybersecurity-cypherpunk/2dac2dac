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
import Icon from "react-native-vector-icons/MaterialCommunityIcons";

export default function Prescription({ route }) {
  const navigation = useNavigation();
  const { userId } = route.params;

  const [item, setitem] = useState([]);
  const [cnt, setCnt] = useState();

  // const handleBlockPress = (id, data) => {
  //   navigation.navigate("PrescriptionInfoStack", { id, data }); // 투약 대면 상세페이지
  // };
  const handleLoad = (userId, data) => {
    navigation.navigate("PrescriptionFaceDetails", { userId, data });
  };

  const fetchData = async () => {
    try {
      const response = await fetch(
        `http://203.252.213.209:8080/api/v1/healthList/prescription/${userId}`
      );
      const data = await response.json();

      setitem(data.data.prescriptionItemList);
      setCnt(data.data.totalCnt);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <View style={[{ height: "100%" }, { backgroundColor: "white" }]}>
      <View style={styles.screenContainer}>
        <View style={styles.row}>
          {cnt === 0 ? (
            <View style={[{ alignItems: "center" }, { paddingTop: 250 }]}>
              <Image
                source={require("../../../assets/images/PatientInfo/ListNonExist.png")}
              />
              <Text
                style={[
                  styles.hospitalName,
                  { paddingTop: 20 },
                  { paddingBottom: 10 },
                ]}
              >
                {" "}
                확인된 처방 내역이 없어요.{" "}
              </Text>
              <Text> 확인된 처방 내역이 없습니다. </Text>
            </View>
          ) : (
            <ScrollView style={styles.scrollView}>
              {item.map((item) => {
                return (
                  <View key={item.prescriptionId} style={styles.hospitalBlock}>
                    <View style={{ flex: 1 }}>
                      <Text style={styles.timeText}>{item.treatDate}</Text>
                      <View style={styles.hospitalInfoContainer}>
                        {/* Doctor's basic information on the right */}
                        <View style={styles.row}>
                          <View style={[{ paddingTop: 5 }]}>
                            <Text style={styles.hospitalName}>
                              {" "}
                              {item.agencyName}{" "}
                            </Text>
                            <Text
                              style={[
                                styles.hospitalInfo,
                                { width: 240 },
                                { paddingTop: 20 },
                              ]}
                              numberOfLines={1}
                              ellipsizeMode="tail"
                            >
                              {item.agencyAddress}
                            </Text>
                            <Text style={styles.hospitalInfo}>
                              {" "}
                              {item.agencyTel}
                            </Text>
                          </View>
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
              })}
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
    height: 150,
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
    fontSize: 13,
    color: "#A3A3A3",
  },
  text: {
    fontSize: 17,
  },
});
