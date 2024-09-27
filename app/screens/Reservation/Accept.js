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
      const apiUrl = `http://203.252.213.209:8080/api/v1/reservations/accept/${doctorId}/${selectedReservation.reservationId}`;
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
          <Text>신청 일시: {formatDate(selectedReservation.signUpDate)}</Text>
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
    justifyContent: "center", // Centers the content vertically
    padding: 20,
    backgroundColor: "#fff",
  },
  title: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 10,
  },
  subtitle: {
    fontSize: 14,
    marginBottom: 20,
    color: "#666",
  },
  detailsBox: {
    marginBottom: 20,
  },
  confirmButton: {
    backgroundColor: "#4CAF50", // Green background
    paddingVertical: 10,
    borderRadius: 5,
    alignItems: "center",
  },
  confirmButtonText: {
    color: "white",
    fontSize: 16,
    fontWeight: "bold",
  },
});

export default Accept;
