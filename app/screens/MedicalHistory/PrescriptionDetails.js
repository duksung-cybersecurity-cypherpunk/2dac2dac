import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Image,
  TextInput
} from "react-native";
import dayjs from "dayjs";
import Icon from "react-native-vector-icons/MaterialCommunityIcons";

export default function PrescriptionDetails({ route }) {
  const { data } = route.params;

  const [name, setName] = useState("");
  const [drug, setDrug] = useState();
  const [symptom, setSymptom] = useState();
  const [disease, setDisease] = useState();
  const [opinion, setOpinion] = useState();
  const [price, setPrice] = useState();
  const [paymentType, setType] = useState("");
  const [date, setDate] = useState("");
  const [item, setItem] = useState([]);
  
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
      setOpinion(itemdata.data.doctorOpinion);
      setPrice(itemdata.data.paymentPrice);
      setType(itemdata.data.paymentType);
      setDate(itemdata.data.paymentAcceptedDate);
      setItem(itemdata.data.medicineList);
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
          <Text style={[styles.titleText, { paddingTop: 30 }]}>의사 소견</Text>
          <View style={styles.opinionBlock}>
              {opinion != null && <Text>{opinion}</Text>}
              <Text>과식은 몸에 해롭습니다. 적당한 식사량을 유지하는 것이 중요합니다.</Text>
          </View>
          <Text style={[styles.titleText, { paddingTop: 30 }]}>처방전</Text>
          {item.map((item, index) => {
            return (
              <View key={index}>
                <View style={[{ flex: 1 }, { paddingTop: 20 }]}>
                  <View
                    style={[
                      { borderBlockColor: "#A3A3A3" },
                      { borderBottomWidth: 0.5 },
                    ]}
                  >
                  <View style={[{ flexDirection: "row" }]}>
                    {/* 의약품 URL로부터 이미지 가져오기 */}
                    <Image 
                      style={[{width: 80}, {height: 80}]} 
                      source={{ uri: item.medicineImageUrl }} // URL 링크로 이미지 표시
                      defaultSource={require("../../../assets/images/PatientInfo/pills.png")} // 로딩 중 기본 이미지
                    />
                    <View
                      style={[
                        { alignItems: "flex-start" },
                        { paddingLeft: 10 },
                      ]}
                    >
                        <Text 
                            style={styles.hospitalName}
                            numberOfLines={2}
                        >{item.medicineName}</Text>
                    </View>
                  </View>

                  <Text>{item.medicineClassName}</Text>
                  <Text numberOfLines={2}>{item.medicineChart}</Text>
                  
                    <View style={[styles.row, { paddingTop: 30 }]}>
                      <View style={styles.blockTop}>
                        <Text>투약일 수</Text>
                      </View>
                      <View style={styles.blockTop}>
                        <Text>일일 투약 횟수</Text>
                      </View>
                    </View>
                    <View style={[styles.row, { paddingBottom: 20 }]}>
                      <View style={styles.blockBottom}>
                        <Text>{item.medicationDays}일</Text>
                      </View>
                      <View style={styles.blockBottom}>
                        <Text>{item.prescriptionCnt}회</Text>
                      </View>
                    </View>
                  </View>
                </View>
              </View>
            );
          })}
          <Text style={[styles.titleText, { paddingTop: 30 }]}>결제 정보</Text>
          <View style={[styles.row, { paddingTop: 10 }]}>
            <Text> 진찰료 </Text>
            <Text> {price}원 </Text>
          </View>
          <View style={[styles.row, { paddingTop: 5 }]}>
            <Text> 결제 방법 </Text>
            <Text> {paymentType} </Text>
          </View>
          <View style={[styles.row, { paddingTop: 5 }, {paddingBottom: 30}]}>
            <Text> 승인 일시 </Text>
            <Text> {dayjs(date).format("YYYY.MM.DD HH:mm")}</Text>
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
  hospitalName: {
    fontSize: 18,
    fontWeight: "bold",
  },
  blockTop: {
    alignItems: "center",
    justifyContent: "center",
    width: "48%",
    height: 35,
    borderTopLeftRadius: 5,
    borderTopRightRadius: 5,
    backgroundColor: "#9BD394",
  },
  blockBottom: {
    alignItems: "center",
    justifyContent: "center",
    width: "48%",
    height: 35,
    borderBottomLeftRadius: 5,
    borderBottomRightRadius: 5,
    backgroundColor: "#EBF2EA",
  },
  opinionBlock: {
    backgroundColor: "#EBF2EA",
    borderRadius: 10,
    padding: 20,
    marginTop: 10,
  },
  doctorInfo: {
    fontSize: 16,
  },
  titleText: {
    fontSize: 22,
    fontWeight: "bold",
  },
  text: {
    fontSize: 15,
  },
});
