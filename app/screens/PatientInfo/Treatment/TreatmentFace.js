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
  console.log("userId22", route.params);
  const [item, setitem] = useState([]);
  const [cnt, setCnt] = useState();
  const [date, setDate] = useState();
  const [time, setTime] = useState();

  const handleLoad = (userId, data) => {
    navigation.navigate("TreatmentInfoStack", { 
      screen: "FaceDetails",
      id: 2,
      params: { userId: userId, data: data } });
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
      setitem(data.data.contactDiagList.contactDiagItemList);
      setCnt(data.data.contactDiagList.totalCnt);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  useEffect(() => {
    if (item.length > 0) {
      item.forEach((item) => {
        if (item.diagDate) {
          const [datePart, timePart] = item.diagDate.split("T");
          const modifiedDatePart = datePart.replace(/-/g, ".");
          setDate(modifiedDatePart);

          const [hour, minute, second] = timePart.split(":");
          setTime(`${hour}:${minute}:${second}`);
        }
      });
    }
  }, [item]);

  return (
    <View style={[{ height: "100%" }, { backgroundColor: "white" }]}>
      <View style={styles.screenContainer}>
        <View style={styles.row}>
          {cnt === 0 ? (
            <View style={[{ alignItems: "center" }, { paddingTop: 250 }]}>
              <Image
                source={require("../../../../assets/images/PatientInfo/ListNonExist.png")}
              />
              <Text
                style={[
                  styles.hospitalName,
                  { paddingTop: 20 },
                  { paddingBottom: 10 },
                ]}
              >
                {" "}
                확인된 진료 내역이 없어요.{" "}
              </Text>
              <Text> Doc'tech을 통해 대면 진료 내역도 관리해보세요. </Text>
            </View>
          ) : (
            <ScrollView style={styles.scrollView}>
              {item.map((item) => (
                <View key={item.diagId} style={styles.hospitalBlock}>
                  <View>
                    <Text style={styles.timeText}>
                      {date} {time}
                    </Text>
                    <View style={styles.hospitalInfoContainer}>
                      <Image
                        source={require("../../../../assets/images/hospital/hospital_all.png")}
                        style={styles.hospitalImage}
                      />
                      <View style={styles.row}>
                        <View style={[{ padding: 5 }]}>
                          <Text style={styles.hospitalName}>
                            {" "}
                            {item.agencyName}{" "}
                          </Text>
                          <Text
                            style={[styles.hospitalInfo, { width: 260 }]}
                            numberOfLines={1}
                            ellipsizeMode="tail"
                          >
                            {item.agencyAddress}
                          </Text>
                          <View
                            style={[{ flexDirection: "row" }, { padding: 3 }]}
                          >
                            {item.agencyIsOpenNow === true ? (
                              <Text style={[styles.text, { color: "#94C973" }]}>
                                {" "}
                                ㆍ영업 중 |{" "}
                              </Text>
                            ) : (
                              <Text style={[styles.text, { color: "#D6D6D6" }]}>
                                {" "}
                                ㆍ영업 종료 |{" "}
                              </Text>
                            )}
                            <Text style={[styles.text, { color: "#D6D6D6" }]}>
                              {" "}
                              {getDayOfWeek()} ({item.agencyTodayOpenTime}
                              {" ~ "}
                              {item.agencyTodayCloseTime}){" "}
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
    paddingTop: 50, // 탭 바 높이만큼 여백 추가
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
  hospitalBlock: {
    flexDirection: "row",
    height: 150,
    backgroundColor: "white",
    padding: 20,
    borderBottomColor: "#D6D6D6",
    borderBottomWidth: 1,
  },
  hospitalImage: {
    width: 70,
    height: 70,
    borderRadius: 5,
    marginTop: 15,
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
    padding: 5,
  },
  timeText: {
    fontSize: 16,
  },
  text: {
    fontSize: 14,
  },
});
