import React, { useEffect, useState } from "react";
import {
  View,
  Text,
  TouchableOpacity,
  ScrollView,
  FlatList,
  StyleSheet,
  Modal,
} from "react-native";
import { useNavigation } from "@react-navigation/native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";
import {
  getCurrentWeekDays,
  formatDate,
  ReservationDate,
} from "../Components/weeks";

export default function Reservation() {
  const navigation = useNavigation();
  const dates = getCurrentWeekDays();
  const [selectedDate, setSelectedDate] = useState(dates[0].name);
  const [selectedTab, setSelectedTab] = useState("요청된 예약");
  const [reservations, setReservations] = useState({
    pending: [],
    accepted: [],
  });
  const [modalVisible, setModalVisible] = useState(false);
  const [doctorId, setDoctorId] = useState(null);
  const [selectedReservation, setSelectedReservation] = useState(null); // New state

  const fetchReservations = async () => {
    try {
      const userInfo = await AsyncStorage.getItem("userInfo");

      const userData = JSON.parse(userInfo);
      console.log("userId", userData, ReservationDate(selectedDate));
      setDoctorId(userData.id);
      const apiUrl = `http://203.252.213.209:8080/api/v1/reservations/${doctorId}/${ReservationDate(
        selectedDate
      )}`;
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

  useEffect(() => {
    fetchReservations();
  }, [selectedDate]);

  const renderReservation = ({ item }) => {
    //console.log("Rendered Item:", item); // Check if item is passed correctly
    const id = item.reservationId;
    console.log("id", item, doctorId);
    return (
      <View style={styles.reservationCard}>
        <Text style={styles.timeText}>{formatDate(item.reservationDate)}</Text>
        <Text style={styles.patientName}>환자: {item.patientName}</Text>
        <Text style={styles.desiredTime}>
          희망 진료 시간: {formatDate(item.reservationDate)}
        </Text>
        <View style={styles.requestRow}>
          <Text>✔ 예약 요청: {item.request}</Text>
        </View>
        {selectedTab === "요청된 예약" && (
          <View style={styles.buttonsRow}>
            <TouchableOpacity
              style={styles.acceptButton}
              onPress={() => {
                // Set the selected reservation item and navigate
                setSelectedReservation(item); // Update the selected reservation

                // Navigate to the Accept screen with the selected reservation item
                navigation.navigate("Reject", {
                  modalVisiblae: true, // Show modal
                  setModalVisible, // Modal visibility function
                  selectedReservation: item, // Pass the selected reservation item
                  doctorId: doctorId, // Pass the doctor's ID
                });
              }}
            >
              <Text style={styles.acceptButtonText}>예약 거절하기</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.acceptButton}
              onPress={() => {
                // Set the selected reservation item and navigate
                setSelectedReservation(item); // Update the selected reservation

                // Navigate to the Accept screen with the selected reservation item
                navigation.navigate("Accept", {
                  modalVisiblae: true, // Show modal
                  setModalVisible, // Modal visibility function
                  selectedReservation: item, // Pass the selected reservation item
                  doctorId: doctorId, // Pass the doctor's ID
                });
              }}
            >
              <Text style={styles.acceptButtonText}>예약 수락하기</Text>
            </TouchableOpacity>
          </View>
        )}
        {selectedTab === "수락된 예약" && (
          <TouchableOpacity
            onPress={() => {
              setSelectedReservation(item); // Set the selected reservation
              navigation.navigate("ReservationDetails", {
                doctorId: doctorId,
                reservationId: item.reservationId, // Pass the reservation ID
              });
            }}
          >
            <Text style={styles.viewDetailsText}>상세보기</Text>
          </TouchableOpacity>
        )}
      </View>
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
              style={[styles.dateText, date.isToday ? styles.todayText : null]}
            >
              {date.name}
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
        renderItem={renderReservation} // Make sure you are passing this function correctly
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: "#fff",
  },
  dateBox: {
    paddingHorizontal: 12,
    paddingVertical: 5,
    borderRadius: 8,
    marginRight: 8,
    height: 100,
    marginBottom: 15,
  },
  selectedDate: {
    backgroundColor: "#5cb85c",
  },
  unselectedDate: {
    backgroundColor: "#f8f9fa",
  },
  dateText: {
    color: "#000",
  },
  todayText: {
    fontWeight: "bold",
  },
  tabHeader: {
    flexDirection: "row",
    justifyContent: "space-around",
    marginVertical: 10,
  },
  tab: {
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderRadius: 20,
    backgrounColor: "#fff",
  },
  selectedTab: {
    backgroundColor: "#5cb85c",
  },

  reservationCard: {
    marginBottom: 10,
    padding: 15,
    borderRadius: 8,
    backgroundColor: "#fff",
    borderWidth: 1, // Border width
    borderColor: "black", // Border color
    margin: 2,
  },
  timeText: {
    fontSize: 16,
    fontWeight: "bold",
  },
  patientName: {
    fontSize: 14,
    marginVertical: 5,
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
    backgroundColor: "#dc3545",
    borderRadius: 5,
  },
  acceptButton: {
    paddingVertical: 8,
    paddingHorizontal: 15,
    backgroundColor: "#28a745",
    borderRadius: 5,
  },
});

// Styles unchanged...
