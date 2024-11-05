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

export default function NonfaceDetails({ route }) {
  const navigation = useNavigation();
  const { userId, data } = route.params;
  const [item, setitem] = useState([]);
  const [diag, setDiag] = useState([]);

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
        `http://203.252.213.209:8080/api/v1/healthList/diagnosis/noncontact/${userId}/${data}`
      );
      const diagdata = await response.json();
      setitem(diagdata.data.noncontactDoctorInfo);
      setDiag(diagdata.data.noncontactDiagDetailInfo);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <View style={styles.screenContainer}>
      <ScrollView style={styles.scrollView}>
        <View style={[{ padding: 20 }]}>
          <Text style={styles.timeText}>{item.diagDate}</Text>
          <View style={[styles.doctorInfoContainer, { paddingBottom: 15 }]}>
            <Image
              source={require("../../../../assets/images/hospital/hospital_all.png")}
              style={styles.doctorImage}
            />
            <View style={[{ padding: 10 }]}>
              <Text style={styles.doctorName}> {item.doctorName} 의사 </Text>
              <View style={[{ padding: 3 }]}></View>
              <Text style={styles.doctorInfo}> {item.doctorHospitalName} </Text>
              <View style={[{ flexDirection: "row" }, { padding: 3 }]}>
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
                  {getDayOfWeek()} ({item.doctorTodayOpenTime}
                  {" ~ "}
                  {item.doctorTodayCloseTime}){" "}
                </Text>
              </View>
            </View>
          </View>
          <Text style={styles.titleText}>진료 세부 정보</Text>
          <View style={[styles.row, { paddingTop: 20 }]}>
            <Text>진료 형태</Text>
            <Text>일반 외래</Text>
          </View>
          <Text style={styles.titleText}>증상</Text>
          <View style={[styles.row, { paddingTop: 20 }]}>
            <Text style={styles.text}> 복용 중인 약 유무 </Text>
            {diag.isPrescribedDrug === true ? (
              <Icon
                name="check"
                size={24}
                color="#76B947"
                style={[{ marginLeft: 100 }]}
              />
            ) : (
              <Icon
                name="check"
                size={24}
                color="#D6D6D6"
                style={[{ marginLeft: 100 }]}
              />
            )}
          </View>
          <View style={[styles.row, { paddingTop: 5 }]}>
            <Text style={styles.text}> 알레르기 유무 </Text>
            {diag.isAllergicSymptom === true ? (
              <Icon
                name="check"
                size={24}
                color="#76B947"
                style={[{ marginLeft: 100 }]}
              />
            ) : (
              <Icon
                name="check"
                size={24}
                color="#D6D6D6"
                style={[{ marginLeft: 100 }]}
              />
            )}
          </View>
          <View style={[styles.row, { paddingTop: 5 }]}>
            <Text style={styles.text}> 선천적인 질환 유무 </Text>
            {diag.isInbornDisease === true ? (
              <Icon
                name="check"
                size={24}
                color="#76B947"
                style={[{ marginLeft: 100 }]}
              />
            ) : (
              <Icon
                name="check"
                size={24}
                color="#D6D6D6"
                style={[{ marginLeft: 100 }]}
              />
            )}
          </View>
          <View
            style={[
              { paddingTop: 20 },
              { paddingBottom: 30 },
              { paddingLeft: 120 },
            ]}
          ></View>

          <Text style={styles.titleText}>의사 소견</Text>
          <View style={[styles.opinionBlock, { paddingBottom: 30 }]}>
            {diag.doctorOpinion != null && <Text>{diag.doctorOpinion}</Text>}
          </View>

          <Text style={styles.titleText}>결제 정보</Text>
          <View style={[styles.row, { paddingTop: 20 }]}>
            <Text> 진찰료 </Text>
            <Text> {diag.price}원 </Text>
          </View>
          <View style={[styles.row, { paddingTop: 5 }]}>
            <Text> 결제 방법 </Text>
            <Text> {diag.paymentType} </Text>
          </View>
          <View style={[styles.row, { paddingTop: 5 }]}>
            <Text> 승인 일시 </Text>
            <Text> {diag.approvalDate} </Text>
          </View>
        </View>
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  screenContainer: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "white",
  },
  row: {
    flex: 1,
    flexDirection: "row",
    justifyContent: "space-between",
  },
  scrollView: {
    flex: 1,
    width: "100%",
  },
  countBlock: {
    flexDirection: "row",
    justifyContent: "space-between",
    paddingTop: 30,
    paddingLeft: 50,
    paddingRight: 50,
  },
  reviewBlock: {
    flexDirection: "row",
    width: "98%",
    height: "20%",
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#EBF2EA",
  },
  blockTop: {
    alignItems: "center",
    justifyContent: "center",
    width: "32%",
    height: 35,
    borderTopLeftRadius: 5,
    borderTopRightRadius: 5,
    backgroundColor: "#9BD394",
  },
  blockBottom: {
    alignItems: "center",
    justifyContent: "center",
    width: "32%",
    height: 35,
    borderBottomLeftRadius: 5,
    borderBottomRightRadius: 5,
    backgroundColor: "#EBF2EA",
  },
  symptomBlock: {
    alignItems: "center",
    justifyContent: "center",
    height: 32,
    borderRadius: 5,
    backgroundColor: "#CFECD9",
  },
  button: {
    width: "60%",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#94C973",
    borderRadius: 8,
    marginRight: 8,
    paddingVertical: 10,
  },
  doctorBlock: {
    flexDirection: "row",
    height: 200,
    backgroundColor: "white",
    padding: 20,
  },
  doctorImage: {
    width: 80,
    height: 80,
    borderRadius: 5,
    marginTop: 10,
  },
  doctorInfoContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  doctorName: {
    fontSize: 18,
    fontWeight: "bold",
  },
  doctorInfo: {
    fontSize: 16,
  },
  timeText: {
    fontSize: 16,
  },
  titleText: {
    fontSize: 24,
    fontWeight: "bold",
  },
  symptomText: {
    fontSize: 14,
    fontWeight: "bold",
  },
  text: {
    fontSize: 15,
  },
});
