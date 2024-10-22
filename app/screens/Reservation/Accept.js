import React from "react";
import { View, Text, TouchableOpacity, StyleSheet } from "react-native";
import axios from "axios";
import { formatDate } from "../../Components/weeks";
import { useNavigation } from "@react-navigation/native";

const Accept = ({ route }) => {
  const { selectedReservation, doctorId } = route.params;
  const navigation = useNavigation();

  const handleAcceptReservation = async () => {
    try {
      const apiUrl = `http://203.252.213.209:8080/api/v1/doctors/reservations/accept/${doctorId}/${selectedReservation.reservationId}`;
      const response = await axios.post(apiUrl);

      if (response.status === 200) {
        console.log("Reservation accepted successfully!");
        navigation.goBack();
      } else {
        console.error("Failed to accept the reservation.");
      }
    } catch (error) {
      console.error("Error while accepting the reservation:", error);
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>예약을 수락하시겠습니까?</Text>
      <Text style={styles.subtitle}>
        예약을 수락하는 즉시 환자에게 알림이 전송됩니다.
      </Text>

      {selectedReservation && (
        <View style={styles.detailsBox}>
          <Text>신청자 명: {selectedReservation.patientName}</Text>
          <Text>
            신청 일시: {formatDate(selectedReservation.reservationDate)}
          </Text>
          <Text>
            희망 일시: {formatDate(selectedReservation.reservationDate)}
          </Text>
        </View>
      )}

      <TouchableOpacity
        style={styles.confirmButton}
        onPress={handleAcceptReservation}
      >
        <Text style={styles.confirmButtonText}>예약 수락하기</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center", // Centers content horizontally
    padding: 20,
    backgroundColor: "#fff",
  },
  title: {
    fontSize: 20,
    fontWeight: "bold",
    color: "#333", // Darker text
    marginBottom: 10,
  },
  subtitle: {
    fontSize: 14,
    color: "#666",
    marginBottom: 20,
    textAlign: "center", // Centered subtitle
  },
  detailsBox: {
    backgroundColor: "#F0F8F5", // Light greenish background
    padding: 15,
    borderRadius: 10,
    marginBottom: 30,
    width: "100%", // Full width
  },
  row: {
    flexDirection: "row",
    justifyContent: "space-between",
    marginBottom: 10,
  },
  label: {
    fontWeight: "bold",
    color: "#333",
  },
  value: {
    color: "#333",
  },
  confirmButton: {
    backgroundColor: "#9BD394",
    paddingVertical: 12,
    paddingHorizontal: 40,
    borderRadius: 10,
  },
  confirmButtonText: {
    color: "#fff",
    fontSize: 16,
    fontWeight: "bold",
  },
});

export default Accept;
