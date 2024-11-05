import React, { useState, useEffect, useCallback } from "react";
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Image,
} from "react-native";
import { useNavigation, useFocusEffect } from "@react-navigation/native";
import dayjs from "dayjs";
import AsyncStorage from "@react-native-async-storage/async-storage";

export default function Home() {
  const navigation = useNavigation();
  const [doctorInfo, setDoctorInfo] = useState(null);
  const [cnt, setCnt] = useState(0);
  const [totalcnt, setTotalCnt] = useState(0);
  const [schedule, setSchedule] = useState([]);

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const userInfo = await AsyncStorage.getItem("userInfo");
        const userData = JSON.parse(userInfo);
        if (userData && userData.id) {
          setDoctorInfo(userData.id);
        }
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

  const fetchData = async () => {
    try {
      const response = await fetch(
        `http://203.252.213.209:8080/api/v1/doctors/reservations/${doctorInfo}/today`
      );
      const data = await response.json();

      const schedule = data.data.scheduledReservationItemList || []; // 기본값 설정
      const num = schedule.reduce((acc, curr) => acc + (curr.length || 0), 0); // 총 객체 수 계산
      setSchedule(schedule);
      setCnt(num);
      setTotalCnt(data.data.totalCnt);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const handleLoad = (doctorId, reservationId) => {
    navigation.navigate("ReservationStack", {
      screen: "ReservationDetails",
      id: 1,
      params: { doctorId: doctorId, reservationId: reservationId },
    });
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.label}>DOC'TECH</Text>
      </View>
      <View style={styles.content}>
        {totalcnt === 0 ? (
          <View style={styles.emptyState}>
            <Image
              source={require("../../assets/images/PatientInfo/ListNonExist.png")}
            />
            <Text style={styles.emptyText}>수락된 진료 내역이 없어요.</Text>
            <Text style={styles.text}>
              {" "}
              놓친 진료 신청 내역이 있는지 확인해보세요.
            </Text>
          </View>
        ) : (
          <ScrollView style={styles.scrollView}>
            <Text style={styles.cntText}>오늘 남은 진료는 {cnt}건 입니다.</Text>
            {schedule != null &&
              schedule.map((item) => (
                <View key={item.reservationId} style={styles.reservationBlock}>
                  <Text style={styles.hospitalName}>
                    환자 {item.patientName}
                  </Text>
                  <Text style={styles.timeText}>
                    희망 진료 시간:{" "}
                    {dayjs(item.reservationDate).format("YYYY.MM.DD HH:mm")}
                  </Text>

                  <TouchableOpacity
                    style={styles.prescriptionBlock}
                    onPress={() =>
                      handleLoad(doctorInfo, item.noncontactDiagId)
                    }
                    activeOpacity={0.7}
                  >
                    <Text style={styles.prescriptionText}>진료신청서 보기</Text>
                  </TouchableOpacity>
                </View>
              ))}
          </ScrollView>
        )}
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "white",
  },
  header: {
    alignItems: "center",
    justifyContent: "center",
    padding: 20,
  },
  label: {
    fontSize: 24,
    fontWeight: "bold",
    paddingTop: 50,
  },
  content: {
    flex: 1,
    padding: 20,
  },
  emptyState: {
    alignItems: "center",
    justifyContent: "center",
    paddingTop: 180,
  },
  emptyText: {
    alignItems: "center",
    justifyContent: "center",
    paddingTop: 20,
    paddingLeft: 20,
    fontWeight: "bold",
    fontSize: 18,
  },
  scrollView: {
    flex: 1,
  },
  reservationBlock: {
    flexDirection: "column",
    backgroundColor: "white",
    padding: 10,
    borderBottomColor: "#D6D6D6",
    borderBottomWidth: 1,
  },
  timeText: {
    fontSize: 14,
    marginBottom: 3,
  },
  hospitalName: {
    fontSize: 18,
    fontWeight: "bold",
    paddingTop: 3,
    marginBottom: 3,
  },
  prescriptionBlock: {
    width: "100%",
    height: 40,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#9BD394",
    borderRadius: 8,
    marginTop: 10,
  },
  prescriptionText: {
    fontSize: 16,
  },
  vaccinInfo: {
    height: 40,
    flexDirection: "row",
    justifyContent: "center", // 왼쪽 정렬
    alignItems: "center",
    backgroundColor: "#EBF2EA",
    borderRadius: 6,
    marginTop: 10,
    marginBottom: 10,
  },
  vaccCert: {
    width: 30,
    height: 30,
    marginRight: 5, // 이미지와 텍스트 간격 추가
  },
  vaccText: {
    fontSize: 14,
    color: "#737373",
  },
  modalContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "rgba(0, 0, 0, 0.5)",
  },
  modalContent: {
    width: 350,
    padding: 30,
    backgroundColor: "white",
    borderRadius: 10,
  },
  modalText: {
    marginBottom: 10,
  },
  input: {
    height: 50,
    borderColor: "gray",
    borderWidth: 1,
    marginBottom: 15,
    paddingLeft: 10,
  },
  buttonContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  button: {
    flex: 1,
    marginHorizontal: 3,
    borderRadius: 6,
  },
  submitButton: {
    backgroundColor: "#9BD394",
    alignItems: "center",
    padding: 10,
  },
  cancelButton: {
    borderWidth: 1,
    borderColor: "#9BD394",
    alignItems: "center",
    padding: 10,
  },
  buttonText: {
    color: "black",
  },
  cntText: {
    fontSize: 20,
    fontWeight: "bold",
    alignItems: "center",
  },
  text: {
    fontSize: 14,
    color: "#737373",
    marginTop: 3,
  },
});
