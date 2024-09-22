import React, { useState } from "react";
import {
  View,
  Text,
  TouchableOpacity,
  ScrollView,
  FlatList,
  StyleSheet,
} from "react-native";
import { useNavigation } from "@react-navigation/native";

// Function to get the current week's weekday names
const getCurrentWeekDays = () => {
  const currentDate = new Date();
  const currentDayIndex = currentDate.getDay(); // Get the current day index (0 for Sunday, 6 for Saturday)
  const week = [];
  const daysOfWeek = [
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
  ];

  for (let i = 0; i < 7; i++) {
    const dayIndex = (currentDayIndex + i) % 7; // Ensure index stays within the 0-6 range
    week.push({
      name: daysOfWeek[dayIndex],
      isToday: i === 0, // Mark today as true for the first day
    });
  }

  return week;
};

const Reservation = () => {
  const navigation = useNavigation(); // Access navigation

  // Current Week Dates
  const dates = getCurrentWeekDays();
  const [selectedDate, setSelectedDate] = useState(dates[0].name);

  // Tabs: "요청된 예약" and "수락된 예약"
  const [selectedTab, setSelectedTab] = useState("요청된 예약");

  // Sample reservations data
  const pendingReservations = [
    {
      id: 1,
      patientName: "김OO",
      desiredTime: "20XX. 0X. XX 오전 00:00",
      request: "예약한 시간보다 빠르게 진료 받고 싶어요.",
    },
    {
      id: 2,
      patientName: "OOO",
      desiredTime: "20XX. 0X. XX 오전 00:00",
      request: "예약한 시간보다 빠르게 진료 받고 싶어요.",
    },
  ];

  const acceptedReservations = [
    {
      id: 3,
      patientName: "박OO",
      desiredTime: "20XX. 0X. XX 오후 02:00",
      request: "정해진 시간에 예약을 받고 싶어요.",
    },
  ];

  // Render reservation item
  const renderReservation = ({ item }) => (
    <View style={styles.reservationCard}>
      <Text style={styles.timeText}>{item.desiredTime}</Text>
      <Text style={styles.patientName}>환자 {item.patientName}</Text>
      <Text style={styles.desiredTime}>희망 진료 시간: {item.desiredTime}</Text>
      <View style={styles.requestRow}>
        <Text>✔ {item.request}</Text>
      </View>
      <View style={styles.buttonsRow}>
        <TouchableOpacity style={styles.rejectButton}>
          <Text style={styles.buttonText}>예약 거절</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.acceptButton}
          onPress={() =>
            navigation.navigate("ReservationStack", {
              screen: "PatientInfo",
              params: { patient: item },
            })
          }
        >
          <Text style={styles.buttonText2}>예약 수락</Text>
        </TouchableOpacity>
      </View>
    </View>
  );

  return (
    <View style={styles.container}>
      {/* Date Navigation */}
      <ScrollView
        horizontal
        showsHorizontalScrollIndicator={false}
        style={styles.dateScroll}
      >
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
              style={[
                styles.dateText,
                date.isToday ? styles.todayText : null, // Highlight today's date
              ]}
            >
              {date.name}
            </Text>
          </TouchableOpacity>
        ))}
      </ScrollView>

      {/* Tabs for "요청된 예약" and "수락된 예약" */}
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

      {/* Reservation List */}
      <FlatList
        data={
          selectedTab === "요청된 예약"
            ? pendingReservations
            : acceptedReservations
        }
        keyExtractor={(item) => item.id.toString()}
        renderItem={renderReservation}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    paddingHorizontal: 16,
    paddingVertical: 16,
  },
  dateScroll: {
    marginBottom: 16,
  },
  dateBox: {
    paddingHorizontal: 12,
    paddingVertical: 8,
    borderRadius: 8,
    marginRight: 8,
  },
  selectedDate: {
    backgroundColor: "#4CAF50", // Green background
  },
  unselectedDate: {
    backgroundColor: "#F0F0F0",
  },
  dateText: {
    fontSize: 16,
    color: "#000",
  },
  todayText: {
    fontWeight: "bold", // Highlight today's date
    color: "#FF0000", // Red color for today
  },
  tabHeader: {
    flexDirection: "row",
    justifyContent: "space-around",
    marginBottom: 16,
    borderBottomWidth: 1,
    borderBottomColor: "#E0E0E0",
  },
  tab: {
    paddingVertical: 12,
  },
  selectedTab: {
    borderBottomWidth: 2,
    borderBottomColor: "#4CAF50", // Green underline for selected tab
  },
  tabText: {
    fontSize: 16,
    fontWeight: "bold",
    color: "#000",
  },
  reservationCard: {
    borderWidth: 1,
    borderColor: "#E0E0E0",
    borderRadius: 8,
    padding: 16,
    marginBottom: 16,
  },
  timeText: {
    fontSize: 14,
    color: "#888",
  },
  patientName: {
    fontSize: 18,
    fontWeight: "bold",
    marginVertical: 8,
  },
  desiredTime: {
    fontSize: 16,
    color: "#333",
    marginBottom: 8,
  },
  requestRow: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 16,
  },
  buttonsRow: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  rejectButton: {
    backgroundColor: "#fff",
    borderColor: "#4CAF50",
    borderWidth: 1,
    padding: 12,
    borderRadius: 8,
  },
  acceptButton: {
    backgroundColor: "#4CAF50",
    padding: 12,
    borderRadius: 8,
  },
  buttonText: {
    color: "#4CAF50",
    fontSize: 16,
    fontWeight: "bold",
  },
  buttonText2: {
    color: "#fff",
    fontSize: 16,
    fontWeight: "bold",
  },
});

export default Reservation;
