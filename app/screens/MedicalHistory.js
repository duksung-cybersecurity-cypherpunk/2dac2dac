import React, { useState, useEffect, useCallback } from "react";
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Image,
} from "react-native";
import dayjs from "dayjs";
import { useNavigation, useFocusEffect } from "@react-navigation/native";
import AsyncStorage from "@react-native-async-storage/async-storage";

export default function MedicalHistory() {
  const navigation = useNavigation();
  const [doctorInfo, setDoctorInfo] = useState(null);
  const [cnt, setCnt] = useState(0);
  const [item, setItem] = useState([]);
  const [toBeItem, setToBeItem] = useState([]);

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const userInfo = await AsyncStorage.getItem("userInfo");
        const userData = JSON.parse(userInfo);
        setDoctorInfo(userData.id);
        fetchData();
      } catch (error) {
        console.error("Error fetching user info:", error);
      }
    };

    fetchUserInfo();
  }, []);

  useEffect(() => {
    if (doctorInfo) {
      fetchData();
    }
  }, [doctorInfo]);

  useFocusEffect(
    useCallback(() => {
      if (doctorInfo) {
        fetchData();
      }
    }, [doctorInfo])
  );

  const handleBlockPress = (data) => {
      navigation.navigate("HistoryStack", {
        screen: "PrescriptionDetails",
        id: 1,
        params: { data: data },
      });
  };
  const handleLoad = (doctorId, reservationId) => {
    navigation.navigate("HistoryStack", {
      screen: "PrescriptionWriting",
      id: 2,
      params: { doctorId: doctorId, reservationId: reservationId },
    });
  };

  const fetchData = async () => {
    try {
      const response = await fetch(`http://203.252.213.209:8080/api/v1/doctors/noncontactDiag/completed/${doctorInfo}`);
      const data = await response.json();
      console.log(data.data.completedReservationList);
      setItem(data.data.completedReservationList);
      setToBeItem(data.data.toBeCompleteReservationList);
      setCnt(data.data.totalCnt);
    } catch (error) {
      console.error("Error fetching data!:", error);
    }
  };

  return (
    <View style={{ height: "100%", backgroundColor: "white" }}>
      <View style={styles.screenContainer}>
        <View style={styles.row}>
          {cnt === 0 ? (
            <View style={{ alignItems: "center", paddingTop: 250 }}>
              <Image
                source={require("../../assets/images/PatientInfo/ListNonExist.png")}
              />
              <Text style={styles.emptyText}>완료된 진료 내역이 없어요.</Text>
            </View>
          ) : (
            <ScrollView style={styles.scrollView}>
              {toBeItem != null &&
                toBeItem.map((toBeItem) => (
                  <View
                    key={toBeItem.noncontactDiagId}
                    style={styles.hospitalBlock}
                  >
                    <View style={{ flex: 1 }}>
                      <Text style={styles.timeText}>
                        {dayjs(toBeItem.reservationDate).format("YYYY.MM.DD HH:mm")}
                      </Text>
                      <Text style={styles.hospitalName}>
                        {toBeItem.patientName} 환자
                      </Text>
                      <TouchableOpacity
                        style={styles.vaccinInfo}
                        onPress={() => handleLoad(doctorInfo, toBeItem.noncontactDiagId)}
                        activeOpacity={0.7}
                      >
                        <Text style={styles.prescriptionText}>처방전 작성하기</Text>
                      </TouchableOpacity>
                    </View>
                  </View>
                ))}
              {item != null &&
                item.map((item) => (
                  <View
                    key={item.noncontactDiagId}
                    style={styles.hospitalBlock}
                  >
                    <View style={{ flex: 1 }}>
                      <Text style={styles.timeText}>
                        {dayjs(item.reservationDate).format("YYYY.MM.DD HH:mm")}
                      </Text>
                      <Text style={styles.hospitalName}>
                        {item.patientName} 환자
                      </Text>
                      <TouchableOpacity
                        style={styles.vaccinInfo}
                        onPress={() => handleBlockPress(item.noncontactDiagId)}
                        activeOpacity={0.7}
                      >
                        <Text style={styles.prescriptionText}>처방전 확인하기</Text>
                      </TouchableOpacity>
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
  hospitalBlock: {
    flexDirection: "row",
    height: 140,
    backgroundColor: "white",
    padding: 20,
    borderBottomColor: "#D6D6D6",
    borderBottomWidth: 1,
  },
  hospitalName: {
    fontSize: 20,
    fontWeight: "bold",
    paddingTop: 5,
  },
  prescriptionText: {
    fontSize: 16,
  },
  timeText: {
    fontSize: 14,
  },
  emptyText: {
    alignItems: "center",
    justifyContent: "center",
    paddingTop: 20,
    paddingLeft: 20, 
    fontWeight: "bold", 
    fontSize: 18
  },
});
