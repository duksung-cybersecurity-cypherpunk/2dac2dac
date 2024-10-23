import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
} from "react-native";
import { useNavigation } from "@react-navigation/native";

export default function ExaminationDetails({ route }) {
  const { userId, data } = route.params;

  const navigation = useNavigation();
  const [examInfo, setExamInfo] = useState([]);
  const [date, setDate] = useState("");

  const handleLoad = (userId, data) => {
    navigation.navigate("ExaminationInfoStack", {
      screen: "MeasurementDetails",
      id: 2,
      params: { userId: userId, data: data },
    });
  };

  const fetchData = async () => {
    try {
      const response = await fetch(
        `http://203.252.213.209:8080/api/v1/healthList/healthScreening/${userId}/${data.hsId}`
      );
      const Examination = await response.json();
      if (Examination.data) {
        setExamInfo(Examination.data.healthScreeningInfo);
        if (data.diagDate) {
          const datePart = data.diagDate.split("T")[0];
          const modifiedDatePart = datePart.replace(/-/g, ". ");
          setDate(modifiedDatePart);
        } else {
          console.error("ExamDate is undefined");
        }
      } else {
        console.error("Examination data is undefined");
      }
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <View style={styles.screenContainer}>
      <View style={styles.hospitalBlock}>
        <View style={{ flex: 1 }}>
          <Text>{date}</Text>
          <Text style={styles.hospitalName}>{data.doctorName} 의사</Text>
          <Text style={styles.hospitalInfo}>{data.doctorHospital}</Text>
        </View>
      </View>
      <Text style={styles.titleText}>건강 검진 결과</Text>
      <View style={styles.healthScreeningBlock}>
        <View style={styles.row}>
          <Text>검진 종류</Text>
          <Text>일반 건강검진</Text>
        </View>
        <View style={styles.row}>
          <Text>검진 의사</Text>
          <Text> {examInfo.doctorName}</Text>
        </View>
        <View style={styles.row}>
          <Text>판정일</Text>
          <Text>{date}</Text>
        </View>
        <View style={styles.row}>
          <Text>검진 병원</Text>
          <Text>{examInfo.doctorHospital}</Text>
        </View>
      </View>
      <Text style={styles.text}>종합 소견</Text>
      <View style={styles.opinionBlock}>
        <ScrollView>
          <Text>{examInfo.opinion}</Text>
        </ScrollView>
      </View>
      <TouchableOpacity
        style={styles.button}
        onPress={() => handleLoad(userId, data)}
        activeOpacity={0.7}
      >
        <Text style={styles.buttonText}>상세 검진 결과 열람하기</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  screenContainer: {
    flex: 1,
    backgroundColor: "white",
  },
  scrollView: {
    flex: 1,
  },
  row: {
    flexDirection: "row",
    justifyContent: "space-between",
    paddingBottom: 5,
  },
  healthScreeningBlock: {
    paddingBottom: 10,
    paddingLeft: 20,
    paddingRight: 20,
  },
  opinionBlock: {
    backgroundColor: "#EBF2EA",
    borderRadius: 10,
    padding: 20,
    marginHorizontal: 20,
  },
  hospitalBlock: {
    flexDirection: "row",
    height: 125,
    padding: 20,
    borderBottomColor: "#EBF2EA",
    borderBottomWidth: 9,
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
  titleText: {
    fontSize: 24,
    fontWeight: "bold",
    padding: 20,
  },
  grayText: {
    color: "#737373",
  },
  button: {
    height: "6%",
    backgroundColor: "#9BD394",
    borderRadius: 8,
    paddingTop: 10,
    alignItems: "center",
    marginTop: 20,
    marginLeft: 90,
    marginRight: 90,
  },
  buttonText: {
    fontSize: 17,
  },
  text: {
    fontSize: 17,
    fontWeight: "bold",
    padding: 20,
  },
});
