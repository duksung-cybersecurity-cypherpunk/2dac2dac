import React, { useState, useEffect, useCallback } from "react";
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Image,
  TextInput,
  Modal,
  Button,
} from "react-native";
import { useNavigation, useFocusEffect } from "@react-navigation/native";
import axios from "axios";
import AsyncStorage from "@react-native-async-storage/async-storage";

export default function Home() {
  const navigation = useNavigation();
  const [doctorInfo, setDoctorInfo] = useState(null);
  const [cnt, setCnt] = useState();
  const [completed, setCompleted] = useState([]);
  const [schedule, setSchedule] = useState([]);
  const [pay, setPay] = useState([]);
  const [modalVisible, setModalVisible] = useState(false);
  const [price, setPrice] = useState("");
  const [selectedItem, setSelectedItem] = useState(null);

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

  const ModalPress = (item) => {
    setSelectedItem(item);

    setModalVisible(true);
  };

  const fetchData = async () => {
    try {
      const response = await fetch(
        `http://203.252.213.209:8080/api/v1/doctors/reservations/${doctorInfo}/today`
      );
      const data = await response.json();
      setCompleted(data.data.completedReservationItemList);
      setSchedule(data.data.scheduledReservationItemList);
      setPay(data.data.toBeCompleteReservationItemList);
      setCnt(data.data.totalCnt);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const handleSubmit = async () => {
    if (!selectedItem) return;
    try {
      const response = await axios.post(
        `http://203.252.213.209:8080/api/v1/doctors/reservations/complete/${doctorInfo}/${selectedItem.reservationId}`,
        {
          doctorId: doctorInfo,
          reservationId: selectedItem.reservationId,
          price: price,
        }
      );
      await fetchData();
      setModalVisible(false);
      setPrice("");
      setSelectedItem(null);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.label}>DOC'TECH</Text>
      </View>
      <View style={styles.content}>
        {cnt === 0 ? (
          <View style={styles.emptyState}>
            <Image
              source={require("../../assets/images/PatientInfo/ListNonExist.png")}
            />
            <Text style={styles.emptyText}>오늘 진료 예약 내역이 없어요.</Text>
          </View>
        ) : (
          <ScrollView style={styles.scrollView}>
            {pay != null &&
              pay.map((item) => (
                <ReservationItem
                  key={item.reservationId}
                  item={item}
                  onPress={ModalPress}
                />
              ))}
            {schedule != null &&
              schedule.map((item) => (
                <ReservationItem key={item.reservationId} item={item} />
              ))}
            {completed != null &&
              completed.map((item) => (
                <CompletedReservationItem
                  key={item.reservationId}
                  item={item}
                />
              ))}
          </ScrollView>
        )}
      </View>
      <ReservationModal
        visible={modalVisible}
        onClose={() => setModalVisible(false)}
        price={price}
        setPrice={setPrice}
        onSubmit={handleSubmit}
      />
    </View>
  );
}

const ReservationItem = ({ item, onPress }) => (
  <View style={styles.reservationBlock}>
    <Text style={styles.timeText}>{item.reservationDate}</Text>
    <Text style={styles.hospitalName}>{item.patientName} 환자</Text>
    {onPress && (
      <TouchableOpacity
        style={styles.prescriptionBlock}
        onPress={() => onPress(item)}
      >
        <Text style={styles.prescriptionText}>진료비 청구하기</Text>
      </TouchableOpacity>
    )}
  </View>
);

const CompletedReservationItem = ({ item }) => (
  <View style={styles.reservationBlock}>
    <Text style={styles.timeText}>{item.reservationDate}</Text>
    <Text style={styles.hospitalName}>{item.patientName} 환자</Text>
    <View style={styles.vaccinInfo}>
      <Image
        source={require("../../assets/images/PatientInfo/vaccCert.png")}
        style={styles.vaccCert}
      />
      <Text style={styles.vaccText}>진료가 완료되었습니다.</Text>
    </View>
  </View>
);

const ReservationModal = ({ visible, onClose, price, setPrice, onSubmit }) => (
  <Modal
    animationType="slide"
    transparent={true}
    visible={visible}
    onRequestClose={onClose}
  >
    <View style={styles.modalContainer}>
      <View style={styles.modalContent}>
        <Text style={styles.modalText}>진료비 금액 입력</Text>
        <TextInput
          style={styles.input}
          placeholder="금액을 입력하세요"
          keyboardType="numeric"
          value={price}
          onChangeText={setPrice}
        />
        <View style={styles.buttonContainer}>
          <TouchableOpacity
            style={[styles.button, styles.submitButton]}
            onPress={onSubmit}
          >
            <Text style={styles.buttonText}>청구하기</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.button, styles.cancelButton]}
            onPress={onClose}
          >
            <Text style={styles.buttonText}>취소</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  </Modal>
);

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
    paddingTop: 160,
  },
  emptyText: {
    paddingTop: 20,
    paddingBottom: 10,
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
    fontSize: 20,
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
});
