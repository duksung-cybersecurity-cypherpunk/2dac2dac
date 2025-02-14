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

export default function TreatmentNonface({ route }) {
  const navigation = useNavigation();
  const { userId } = route.params;

  const [item, setitem] = useState([]);
  const [cnt, setCnt] = useState();

  const [error, setError] = useState(0);
  
  const handleLoad = (userId, data) => {
    navigation.navigate("TreatmentInfoStack", {
      screen: "NonfaceDetails",
      id: 1,
      params: { userId: userId, data: data },
    });
  };
  const getDayOfWeek = () => {
    const daysOfWeek = [
      "일요일",
      "월요일",
      "화요일",
      "수요일",
      "목요일",
      "금요일",
      "토요일",
    ];
    const today = new Date();
    return daysOfWeek[today.getDay()];
  };

  const fetchData = async () => {
    try {
      const response = await fetch(
        `http://203.252.213.209:8080/api/v1/healthList/diagnosis/${userId}`
      );
      const data = await response.json();
      setitem(data.data.noncontactDiagList.noncontactDiagItemList);
      setCnt(data.data.noncontactDiagList.totalCnt);
    } catch (error) {
      setError(1);
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
          {cnt === 0  || error === 1 ? (
            <View style={[{ alignItems: "center" }, { paddingTop: 250 }]}>
              <Image
                source={require("../../../../assets/images/PatientInfo/ListNonExist.png")}
              />
              <Text
                style={[
                  styles.doctorName,
                  { paddingTop: 20 },
                  { paddingBottom: 10 },
                ]}
              >
                {" "}
                아직 진행한 예약이 없어요.{" "}
              </Text>
              <Text> 빠르고 간편한 Doc'tech 진료를 이용해보세요. </Text>
            </View>
          ) : (
            <ScrollView style={styles.scrollView}>
              {item.map((item) => {
                return (
                  <View key={item.diagId} style={styles.doctorBlock}>
                    <View>
                      <Text style={styles.timeText}>{item.diagDate}</Text>
                      <View style={styles.doctorInfoContainer}>
                        <Image
                          source={require("../../../../assets/images/doctor/doctor_all.png")}
                          style={styles.doctorImage}
                        />
                        <View style={styles.row}>
                          <View style={[{ padding: 5 }]}>
                            <Text style={styles.doctorName}>
                              {" "}
                              {item.doctorName} 의사{" "}
                            </Text>
                            <View
                              style={[{ flexDirection: "row" }, { padding: 3 }]}
                            ></View>
                            <Text style={styles.doctorInfo}>
                              {" "}
                              {item.doctorHostpital}{" "}
                            </Text>
                            <View
                              style={[{ flexDirection: "row" }, { padding: 3 }]}
                            >
                              {item.doctorIsOpenNow === true ? (
                                <Text
                                  style={[styles.text, { color: "#94C973" }]}
                                >
                                  {" "}
                                  ㆍ영업 중 |{" "}
                                </Text>
                              ) : (
                                <Text
                                  style={[styles.text, { color: "#D6D6D6" }]}
                                >
                                  {" "}
                                  ㆍ영업 종료 |{" "}
                                </Text>
                              )}
                              <Text style={[styles.text, { color: "#D6D6D6" }]}>
                                {" "}
                                {getDayOfWeek()} ({item.doctorTodayOpenTime}
                                {" ~ "}
                                {item.doctorTodayCloseTime}){" "}
                              </Text>
                            </View>
                          </View>
                        </View>
                        <TouchableOpacity
                          onPress={() => handleLoad(userId, item.diagId)}
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
    paddingTop: 50, // 탭 바 높이만큼 여백 추가
  },
  blocks: {
    width: "49%",
    height: "40%",
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
    marginRight: 10,
  },
  scrollView: {
    flex: 1,
    width: "100%",
  },
  doctorBlock: {
    flexDirection: "row",
    height: 150,
    backgroundColor: "white",
    padding: 20,
    borderBottomColor: "#D6D6D6",
    borderBottomWidth: 1,
  },
  doctorImage: {
    width: 70,
    height: 70,
    borderRadius: 5,
    marginTop: 15,
  },
  doctorInfoContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  doctorName: {
    fontSize: 20,
    fontWeight: "bold",
  },
  doctorInfo: {
    fontSize: 15,
    padding: 5,
  },
  timeText: {
    fontSize: 16,
  },
  text: {
    fontSize: 14,
  },
});
