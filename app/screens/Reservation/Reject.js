import React, { useState } from "react";
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  TextInput,
} from "react-native";
import axios from "axios";
import { formatDate } from "../../Components/weeks";
import { useNavigation } from "@react-navigation/native";

const Reject = ({ route }) => {
  const { selectedReservation, doctorId } = route.params;
  const navigation = useNavigation();
  const [rejectionReason, setRejectionReason] = useState(""); // State for rejection reason
  const [additionalReason, setAdditionalReason] = useState(""); // State for additional reason

  const handleRejectReservation = async () => {
    try {
      const apiUrl = `http://203.252.213.209:8080/api/v1/doctors/reservations/reject/${doctorId}/${selectedReservation.reservationId}`;
      const response = await axios.post(apiUrl, {
        rejectionReason: "DUPLICATE_RESERVATION", // Add rejection reason
        additionalReason: additionalReason, // Add additional reason
      });

      if (response.status === 200) {
        console.log("Reservation rejected successfully!");
        navigation.goBack();
      } else {
        console.error("Failed to reject the reservation.");
      }
    } catch (error) {
      console.error("Error while rejecting the reservation:", error);
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>예약을 거절하시겠습니까?</Text>
      <Text style={styles.subtitle}>예약을 거절하는 이유를 입력해 주세요.</Text>

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

      <TextInput
        style={styles.input}
        placeholder="선택사항 : 거절 이유 (ex. DUPLICATE_RESERVATION)"
        value={rejectionReason}
        onChangeText={setRejectionReason}
      />

      <TextInput
        style={styles.input}
        placeholder="선택사항 : 추가적인 이유 (선택 사항)"
        value={additionalReason}
        onChangeText={setAdditionalReason}
      />

      <TouchableOpacity
        style={styles.confirmButton}
        onPress={handleRejectReservation} // Call the API on button press
      >
        <Text style={styles.confirmButtonText}>예약 거절하기</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    padding: 20,
    backgroundColor: "#fff",
  },
  title: {
    fontSize: 20,
    fontWeight: "bold",
    color: "#333", // Darker text
    marginBottom: 10,
    textAlign: "center",
  },
  subtitle: {
    fontSize: 14,
    color: "#666",
    marginBottom: 20,
    textAlign: "center",
  },
  detailsBox: {
    backgroundColor: "#F0F8F5", // Light greenish background
    padding: 15,
    borderRadius: 10,
    marginBottom: 30,
    justifyContent: "center",
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
    alignItems: "center",
  },
  confirmButtonText: {
    color: "#fff",
    fontSize: 16,
    fontWeight: "bold",
    justifyContent: "center",
  },
  input: {
    marginBottom: 10,
    padding: 15,
    borderRadius: 8,
    backgroundColor: "#fff",
    borderWidth: 1, // Border width
    borderColor: "#ccc", // Border color
    margin: 2,
    justifyContent: "center",
    alignItems: "left",
  },
});

export default Reject;
