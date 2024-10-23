import React, { useState, useEffect } from "react";
import { View, Text, StyleSheet, ScrollView } from "react-native";
import Icon from "react-native-vector-icons/MaterialCommunityIcons";

export default function PrescriptionDetails({ route }) {
  const { data } = route.params;

  const [name, setName] = useState("");
  const [drug, setDrug] = useState();
  const [symptom, setSymptom] = useState();
  const [disease, setDisease] = useState();
  const [price, setPrice] = useState();
  const [paymentType, setType] = useState("");
  const [date, setDate] = useState("");

  const fetchData = async () => {
    try {
      const response = await fetch(
        `http://203.252.213.209:8080/api/v1/doctors/noncontactDiag/prescription/${data}`
      );
      const itemdata = await response.json();

      setName(itemdata.data.patientName);
      setDrug(itemdata.data.isPrescribedDrug);
      setSymptom(itemdata.data.isAllergicSymptom);
      setDisease(itemdata.data.isInbornDisease);
      setPrice(itemdata.data.paymentPrice);
      setType(itemdata.data.paymentType);
      setDate(itemdata.data.paymentAcceptedDate);
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
          <Text style={styles.titleText}>진료 세부 정보</Text>
          <View style={[styles.row, { paddingTop: 10 }]}>
            <Text>진료 형태</Text>
            <Text>일반 외래</Text>
          </View>
          <Text style={[styles.titleText, { paddingTop: 30 }]}>증상</Text>
          <View style={[styles.row, { paddingTop: 10 }]}>
            <Text style={styles.text}> 복용 중인 약 유무 </Text>
            {drug === true ? (
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
            {symptom === true ? (
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
            {disease === true ? (
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
          <Text style={[styles.titleText, { paddingTop: 30 }]}>결제 정보</Text>
          <View style={[styles.row, { paddingTop: 10 }]}>
            <Text> 진찰료 </Text>
            <Text> {price}원 </Text>
          </View>
          <View style={[styles.row, { paddingTop: 5 }]}>
            <Text> 결제 방법 </Text>
            <Text> {paymentType} </Text>
          </View>
          <View style={[styles.row, { paddingTop: 5 }]}>
            <Text> 승인 일시 </Text>
            <Text> {date} </Text>
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
    height: 160,
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
    fontSize: 22,
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
