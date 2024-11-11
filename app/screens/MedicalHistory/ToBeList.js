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

export default function ToBeList({ route }) {
  const navigation = useNavigation();
  const { doctorInfo } = route.params;
  console.log("doctorId", doctorInfo);

  const [cnt, setCnt] = useState(0);
  const [toBeItem, setToBeItem] = useState([]);
  const [error, setError] = useState(0);

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

  const handleLoad = (doctorId, reservationId) => {
    navigation.navigate("HistoryStack", {
      screen: "PrescriptionWriting",
      id: 2,
      params: { doctorId: doctorId, reservationId: reservationId },
    });
  };

  const fetchData = async () => {
    try {
      const response = await fetch(
        `http://203.252.213.209:8080/api/v1/doctors/noncontactDiag/completed/${doctorInfo}`
      );
      const data = await response.json();
      const toBeCompleteReservationList = data.data.toBeCompleteReservationList;
      setToBeItem(toBeCompleteReservationList);

      // num 계산을 여기서 수행
      const num = toBeCompleteReservationList.length;
      setCnt(num);
    } catch (error) {
      setError(1);
      console.error("Error fetching data!:", error);
    }
  };

  return (
    <View style={{ height: "100%", backgroundColor: "white" }}>
      <View style={styles.screenContainer}>
        <View style={styles.row}>
          {cnt === 0 || error === 1 ? (
            <View style={{ alignItems: "center", paddingTop: 250 }}>
              <Image
                source={require("../../../assets/images/PatientInfo/ListNonExist.png")}
              />
              <Text style={styles.emptyText}>진행중인 진료 내역이 없어요.</Text>
              <Text style={styles.text}>
              {"      "}
              놓친 진료 신청 내역이 있는지 확인해보세요.
            </Text>
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
                        {dayjs(toBeItem.reservationDate).format(
                          "YYYY.MM.DD HH:mm"
                        )}
                      </Text>
                      <Text style={styles.hospitalName}>
                        {toBeItem.patientName} 환자
                      </Text>
                      <TouchableOpacity
                        style={styles.vaccinInfo}
                        onPress={() =>
                          handleLoad(doctorInfo, toBeItem.reservationId)
                        }
                        activeOpacity={0.7}
                      >
                        <Text style={styles.prescriptionText}>
                          처방전 작성하기
                        </Text>
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
    fontSize: 18,
  },
  text: {
    fontSize: 14,
    color: "#737373",
    marginTop: 3,
  },
});
