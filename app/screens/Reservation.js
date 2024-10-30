import React, { useEffect, useState } from "react";
import {
  View,
  Text,
  TouchableOpacity,
  ScrollView,
  FlatList,
  StyleSheet,
} from "react-native";
import { useNavigation } from "@react-navigation/native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";
import dayjs from 'dayjs';
import {
  getCurrentWeekDays,
  formatDate,
  ReservationDate,
} from "../Components/weeks";
import ReservationModal from "./Reservation/ReservationModal";

export default function Reservation() {
  const navigation = useNavigation();
  const dates = getCurrentWeekDays();

  const [selectedDate, setSelectedDate] = useState(dates[0].name);
  const [selectedTab, setSelectedTab] = useState("요청된 예약");
  const [reservations, setReservations] = useState({
    pending: [],
    accepted: [],
  });
  const [isModalVisible, setModalVisible] = useState(false);
  const [modalType, setModalType] = useState(""); // 모달 종류 (수락/거절)
  const [doctorId, setDoctorId] = useState(null);
  const [selectedReservation, setSelectedReservation] = useState(null); // 선택된 예약 정보

  useEffect(() => {
    fetchReservations();
  }, [selectedDate, selectedTab]);

  const fetchReservations = async () => {
    try {
      const userInfo = await AsyncStorage.getItem("userInfo");
      const userData = JSON.parse(userInfo);
      setDoctorId(userData.id);

      const apiUrl = `http://203.252.213.209:8080/api/v1/doctors/reservations/${doctorId}/${ReservationDate(selectedDate)}`;
      const response = await axios.get(apiUrl);

      if (response.data.status === 200) {
        const { requestReservationList, acceptedReservationList } =
          response.data.data;
        setReservations({
          pending: requestReservationList.requestReservationItemList,
          accepted: acceptedReservationList.acceptedReservationItemList,
        });
      } else {
        console.error("Failed to fetch reservations:", response.data.message);
      }
    } catch (error) {
      console.error("Error fetching reservations:", error);
    }
  };

  const renderReservation = ({ item }) => {
    const id = item.reservationId;

    return (
    <ScrollView style={styles.scrollView}>
      <View key={id.toString()} style={styles.reservationCard}>
        <Text style={styles.timeText}>{dayjs(item.signupDate).format('YYYY.MM.DD HH:mm')}</Text>
        <Text style={styles.patientName}>환자 {item.patientName}</Text>
        <Text style={styles.desiredTime}>
          희망 진료 시간: {dayjs(item.reservationDate).format('YYYY.MM.DD HH:mm')}
        </Text>

        {selectedTab === "요청된 예약" && (
          <View style={styles.buttonsRow}>
            <TouchableOpacity
              style={styles.rejectButton}
              onPress={() => {
                setSelectedReservation(item);
                setModalType("reject"); // 거절 모달 설정
                setModalVisible(true); // 모달 열기
              }}
            >
              <Text style={styles.acceptButtonText}>예약 거절하기</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.acceptButton}
              onPress={() => {
                setSelectedReservation(item);
                setModalType("accept"); // 수락 모달 설정
                setModalVisible(true); // 모달 열기
              }}
            >
              <Text style={styles.acceptButtonText}>예약 수락하기</Text>
            </TouchableOpacity>
          </View>
        )}
        {selectedTab === "수락된 예약" && (
          <TouchableOpacity
            style={styles.DetailButton}
            onPress={() => {
              setSelectedReservation(item);
              navigation.navigate("ReservationDetails", {
                doctorId: doctorId,
                reservationId: item.reservationId,
              });
            }}
          >
            <Text style={styles.viewDetailsText}>상세보기</Text>
          </TouchableOpacity>
        )}
      </View>
    </ScrollView>
    );
  };

  return (
    <View style={styles.container}>
      <ScrollView horizontal style={styles.dateScroll}>
        {dates.map((date) => (
          <TouchableOpacity
            key={date.name}
            style={[
              styles.dateBox,
              date.name === selectedDate
                ? styles.selectedDate
                : styles.unselectedDate,
            ]}
            onPress={() => setSelectedDate(date.name)}
          >
            <Text
              style={[styles.dayText, date.isToday ? styles.todayText : null]}
            >
              {date.name.slice(0, 3).toUpperCase()}{" "}
            </Text>
            <Text
              style={[styles.dateText, date.isToday ? styles.todayText : null]}
            >
              {date.date.split("-")[2]}
            </Text>
          </TouchableOpacity>
        ))}
      </ScrollView>

      <View style={styles.tabHeader}>
        <TouchableOpacity
          style={[
            styles.tab,
            selectedTab === "요청된 예약" && styles.selectedTab,
          ]}
          onPress={() => setSelectedTab("요청된 예약")}
        >
          <Text style={styles.tabText}>요청된 예약</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={[
            styles.tab,
            selectedTab === "수락된 예약" && styles.selectedTab,
          ]}
          onPress={() => setSelectedTab("수락된 예약")}
        >
          <Text style={styles.tabText}>수락된 예약</Text>
        </TouchableOpacity>
      </View>

      <FlatList
        data={
          selectedTab === "요청된 예약"
            ? reservations.pending
            : reservations.accepted
        }
        keyExtractor={(item) => item.reservationId.toString()}
        renderItem={renderReservation}
      />

      {/* ReservationModal 호출 */}
      <ReservationModal
        modalVisible={isModalVisible}
        setModalVisible={setModalVisible}
        modalType={modalType}
        selectedReservation={selectedReservation}
        doctorId={doctorId}
        fetchReservations={fetchReservations}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: "white",
  },
  dateBox: {
    paddingHorizontal: 12,
    paddingVertical: 5,
    borderRadius: 8,
    marginRight: 8,
    height: 70,
    width: 70,
    marginBottom: 15,
    alignItems: "center",
  },
  selectedDate: {
    backgroundColor: "#9BD394",
  },
  unselectedDate: {
    borderWidth: 1, // 테두리 두께
    borderColor: "#9BD394", // 테두리 색상
    borderRadius: 5,
  },
  dayText: {
    color: "#000",
    fontWeight: "bold",
    textAlign: "center",
    marginTop: 5,
  },
  dateText: {
    color: "#000",
    textAlign: "center",
  },
  todayText: {
    fontWeight: "bold",
    textAlign: "center",
  },
  tabHeader: {
    flexDirection: "row",
    justifyContent: "space-around",
    marginVertical: 10,
    backgroundColor: "#fff",
  },
  tab: {
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderRadius: 20,
    backgrounColor: "#fff",
  },
  selectedTab: {
    backgroundColor: "#9BD394",
  },

  reservationCard: {
    padding: 15,
    borderRadius: 8,
    backgroundColor: "#fff",
    borderWidth: 1, // Border width
    borderColor: "#ccc", // Border color
    margin: 2,
  },

  timeText: {
    fontSize: 11,
  },
  patientName: {
    fontSize: 16,
    marginVertical: 5,
    fontWeight: "bold",
  },
  desiredTime: {
    fontSize: 14,
  },
  requestRow: {
    flexDirection: "row",
    justifyContent: "space-between",
    marginVertical: 10,
  },
  buttonsRow: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  rejectButton: {
    paddingVertical: 8,
    paddingHorizontal: 15,
    borderWidth: 1, // 테두리 두께
    borderColor: "#9BD394", // 테두리 색상
    borderRadius: 5,
    width: "45%",
    height: "50px",
    alignItems: "center", // Centers the content horizontally
    justifyContent: "center",
    marginLeft: 10,
    marginTop: 10,
  },
  acceptButton: {
    paddingVertical: 8,
    paddingHorizontal: 15,
    backgroundColor: "#9BD394",
    borderRadius: 5,
    width: "45%",
    height: "50px",
    alignItems: "center", // Centers the content horizontally
    justifyContent: "center",
    marginRight: 10,
    marginTop: 10,
  },
  DetailButton: {
    paddingVertical: 8,
    paddingHorizontal: 15,
    backgroundColor: "#9BD394",
    borderRadius: 5,
    width: "100%",
    height: "32%",
    alignItems: "center", // Centers the content horizontally
    justifyContent: "center",
    marginTop: 10,
  },
});