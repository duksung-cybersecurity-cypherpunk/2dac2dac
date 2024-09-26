import React, { useState } from "react";
import {
  View,
  Text,
  TouchableOpacity,
  Modal,
  StyleSheet,
  TextInput,
} from "react-native";
import axios from "axios";
import { formatDate } from "../../Components/weeks";

const Reject = ({ route }) => {
  const { modalVisible, setModalVisible, selectedReservation, doctorId } =
    route.params;

  console.log("Received selectedReservation:", selectedReservation, doctorId); // Log received props

  const [rejectionReason, setRejectionReason] = useState(""); // State for rejection reason
  const [additionalReason, setAdditionalReason] = useState(""); // State for additional reason

  const handleRejectReservation = async () => {
    try {
      const apiUrl = `http://203.252.213.209:8080/api/v1/reservations/reject/${doctorId}/${selectedReservation.reservationId}`;
      const response = await axios.post(apiUrl, {
        rejectionReason: rejectionReason, // Add rejection reason
        additionalReason: additionalReason, // Add additional reason
      });

      if (response.status === 200) {
        console.log("Reservation rejected successfully!");
        setModalVisible(false); // Close modal after successful rejection
      } else {
        console.error("Failed to reject the reservation.");
      }
    } catch (error) {
      console.error("Error while rejecting the reservation:", error);
    }
  };

  return (
    <Modal
      animationType="slide"
      transparent={true}
      visible={modalVisible}
      onRequestClose={() => setModalVisible(false)}
    >
      <View style={styles.modalOverlay}>
        <View style={styles.modalContent}>
          <Text style={styles.modalTitle}>예약을 거절하시겠습니까?</Text>
          <Text style={styles.modalSubtitle}>
            예약을 거절하는 이유를 입력해 주세요.
          </Text>

          {selectedReservation && (
            <View style={styles.detailsBox}>
              <Text>신청자 명: {selectedReservation.patientName}</Text>
              <Text>
                신청 일시: {formatDate(selectedReservation.signUpDate)}
              </Text>
              <Text>
                희망 일시: {formatDate(selectedReservation.reservationDate)}
              </Text>
            </View>
          )}

          {/* Rejection Reason Input */}
          <TextInput
            style={styles.input}
            placeholder="거절 이유 (ex. DUPLICATE_RESERVATION)"
            value={rejectionReason}
            onChangeText={setRejectionReason} // Update state on change
          />

          {/* Additional Reason Input */}
          <TextInput
            style={styles.input}
            placeholder="추가적인 이유 (선택 사항)"
            value={additionalReason}
            onChangeText={setAdditionalReason} // Update state on change
          />

          <TouchableOpacity
            style={styles.confirmButton}
            onPress={handleRejectReservation} // Call the API on button press
          >
            <Text style={styles.confirmButtonText}>예약 거절하기</Text>
          </TouchableOpacity>
        </View>
      </View>
    </Modal>
  );
};

const styles = StyleSheet.create({
  modalOverlay: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "rgba(0, 0, 0, 0.5)",
  },
  modalContent: {
    width: "80%",
    padding: 20,
    backgroundColor: "white",
    borderRadius: 10,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
    elevation: 5,
  },
  modalTitle: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 10,
  },
  modalSubtitle: {
    fontSize: 14,
    marginBottom: 20,
    color: "#666",
  },
  detailsBox: {
    marginBottom: 20,
  },
  input: {
    height: 40,
    borderColor: "gray",
    borderWidth: 1,
    borderRadius: 5,
    paddingHorizontal: 10,
    marginBottom: 15,
  },
  confirmButton: {
    backgroundColor: "#dc3545", // Red background for rejection
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

export default Reject;
