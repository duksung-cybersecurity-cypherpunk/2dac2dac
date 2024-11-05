import React, { useState, useEffect } from "react";
import { View, Text, StyleSheet, ScrollView, Image } from "react-native";

export default function PrescriptionNonfaceDetails({ route }) {
  const { userId, data } = route.params;
  const [item, setItem] = useState([]);

  const fetchData = async () => {
    try {
      const response = await fetch(
        `http://203.252.213.209:8080/api/v1/healthList/prescription/noncontact/${userId}/${data.noncontactPrescriptionId}`
      );
      const prescription = await response.json();
      if (prescription.data) {
        setItem(prescription.data.medicines);
        console.log(item);
      } else {
        console.error("Prescription data is undefined");
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
      <View
        style={[
          { padding: 20 },
          { borderBlockColor: "#EBF2EA" },
          { borderBottomWidth: 9 },
        ]}
      >
        <Text style={styles.timeText}>{data.treatDate}</Text>
        <Text style={[styles.hospitalName, { paddingTop: 5 }]}>
          {data.agencyName}
        </Text>
        <Text
          style={[styles.hospitalInfo, { paddingTop: 15 }]}
          numberOfLines={1}
          ellipsizeMode="tail"
        >
          {data.agencyAddress}
        </Text>
        <Text style={styles.hospitalInfo}>{data.agencyTel}</Text>
      </View>
      <Text style={[styles.titleText, { paddingTop: 10 }, { paddingLeft: 20 }]}>
        처방전
      </Text>
      <ScrollView style={[styles.scrollView, { padding: 15 }]}>
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
                      defaultSource={require("../../../../assets/images/PatientInfo/pills.png")} // 로딩 중 기본 이미지
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
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  screenContainer: {
    flex: 1,
    backgroundColor: "white",
  },
  row: {
    flexDirection: "row",
    justifyContent: "space-between",
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
  hospitalImage: {
    width: 80,
    height: 80,
    borderRadius: 5,
    marginTop: 10,
  },
  hospitalName: {
    fontSize: 18,
    fontWeight: "bold",
  },
  hospitalInfo: {
    fontSize: 13,
    color: "#A3A3A3",
  },
  timeText: {
    fontSize: 16,
  },
  titleText: {
    fontSize: 24,
    fontWeight: "bold",
  },
  text: {
    fontSize: 15,
  },
});
