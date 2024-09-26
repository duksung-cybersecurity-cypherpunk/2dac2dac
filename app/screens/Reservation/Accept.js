import React, { useState } from "react";
import { View, Text, TouchableOpacity, Modal, StyleSheet } from "react-native";
import axios from "axios";
import {
  getCurrentWeekDays,
  formatDate,
  ReservationDate,
} from "../../Components/weeks";

const Accept = ({ route }) => {
  const { modalVisible, setModalVisible, selectedReservation, doctorId } =
    route.params;

  console.log("Received selectedReservation:", selectedReservation, doctorId); // 여기에 확인 로그 추가

  const handleAcceptReservation = async () => {
    try {
      const apiUrl = `http://203.252.213.209:8080/api/v1/reservations/accept/${doctorId}/${selectedReservation.reservationId}`;
      const response = await axios.post(apiUrl);

      if (response.status === 200) {
        console.log("Reservation accepted successfully!");
        setModalVisible(false); // Close modal after successful acceptance
      } else {
        console.error("Failed to accept the reservation.");
      }
    } catch (error) {
      console.error("Error while accepting the reservation:", error);
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
          <Text style={styles.modalTitle}>예약을 수락하시겠습니까?</Text>
          <Text style={styles.modalSubtitle}>
            예약을 수락하는 즉시 환자에게 알림이 전송됩니다.
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

          <TouchableOpacity
            style={styles.confirmButton}
            onPress={handleAcceptReservation} // Call the API on button press
          >
            <Text style={styles.confirmButtonText}>예약 수락하기</Text>
          </TouchableOpacity>
        </View>
      </View>
    </Modal>
  );
};

const styles = StyleSheet.create({
  modalOverlay: {
    flex: 1,
    justifyContent: "center", // Centers the modal vertically
    alignItems: "center", // Centers the modal horizontally
    backgroundColor: "rgba(0, 0, 0, 0.5)", // Semi-transparent background
  },
  modalContent: {
    width: "80%",
    padding: 20,
    backgroundColor: "white", // White background for the modal
    borderRadius: 10,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
    elevation: 5, // Adds shadow for Android
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
