import React, { useState } from "react";
import { View, Text, TouchableOpacity, TextInput, StyleSheet, Modal } from "react-native";
import axios from "axios";
import dayjs from 'dayjs';
import { formatDate } from "../../Components/weeks";

const ReservationModal = ({ selectedReservation, modalType, modalVisible, setModalVisible, doctorId, fetchReservations }) => {
  const [rejectionReason, setRejectionReason] = useState("");
  const [additionalReason, setAdditionalReason] = useState("");

  const handleAcceptReservation = async () => {
    try {
      const apiUrl = `http://203.252.213.209:8080/api/v1/doctors/reservations/accept/${doctorId}/${selectedReservation.reservationId}`;
      const response = await axios.post(apiUrl);

      if (response.status === 200) {
        console.log("Reservation accepted successfully!");
        setModalVisible(false);
        fetchReservations();
      } else {
        console.error("Failed to accept the reservation.");
      }
    } catch (error) {
      console.error("Error while accepting the reservation:", error);
    }
  };

  const handleRejectReservation = async () => {
    try {
      const apiUrl = `http://203.252.213.209:8080/api/v1/doctors/reservations/reject/${doctorId}/${selectedReservation.reservationId}`;
      const response = await axios.post(apiUrl, {
        rejectionReason: "DUPLICATE_RESERVATION",
        additionalReason: additionalReason,
      });

      if (response.status === 200) {
        console.log("Reservation rejected successfully!");
        setModalVisible(false);
        fetchReservations();
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
      <View style={styles.modalContainer}>
        <View style={styles.modalContent}>
          <Text style={styles.title}>
            {modalType === "accept" ? "예약을 수락하시겠습니까?" : "예약을 거절하시겠습니까?"}
          </Text>
          <Text style={styles.subtitle}>
            {modalType === "accept"
              ? "예약을 수락하는 즉시 환자에게 알림이 전송됩니다."
              : "예약을 거절하는 이유를 입력해 주세요."}
          </Text>

          {selectedReservation && (
            <View style={styles.detailsBox}>
              <Text>신청자 명: {selectedReservation.patientName}</Text>
              <Text>신청 일시: {dayjs(selectedReservation.signupDate).format('YYYY.MM.DD HH:mm')}</Text>
              <Text>희망 일시: {dayjs(selectedReservation.reservationDate).format('YYYY.MM.DD HH:mm')}</Text>
            </View>
          )}

          {modalType === "reject" && (
            <>
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
            </>
          )}

          <View style={styles.modalButtonsRow}>
            <TouchableOpacity
              style={styles.confirmButton}
              onPress={modalType === "accept" ? handleAcceptReservation : handleRejectReservation}
            >
              <Text style={styles.confirmButtonText}>
                {modalType === "accept" ? "예약 수락하기" : "예약 거절하기"}
              </Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.cancelButton}
              onPress={() => setModalVisible(false)}
            >
              <Text style={styles.confirmButtonText}>취소</Text>
            </TouchableOpacity>
          </View>
        </View>
      </View>
    </Modal>
  );
};

const styles = StyleSheet.create({
  modalContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "rgba(0, 0, 0, 0.5)",
  },
  modalContent: {
    width: 300,
    padding: 20,
    backgroundColor: "white",
    borderRadius: 10,
    alignItems: "center",
  },
  title: {
    fontSize: 20,
    fontWeight: "bold",
    color: "#333",
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
    backgroundColor: "#F0F8F5",
    padding: 15,
    borderRadius: 10,
    marginBottom: 20,
    width: "100%",
  },
  input: {
    marginBottom: 10,
    padding: 8,
    borderRadius: 8,
    backgroundColor: "#fff",
    borderWidth: 1,
    borderColor: "#ccc",
    margin: 2,
    width: "100%",
    justifyContent: "center",
    alignItems: "left",
  },
  modalButtonsRow: {
    flexDirection: "row",
    justifyContent: "space-between",
    width: "100%",
    marginTop: 10,
  },
  confirmButton: {
    backgroundColor: "#9BD394",
    paddingVertical: 12,
    paddingHorizontal: 20,
    borderRadius: 10,
    alignItems: "center",
  },
  cancelButton: {
    backgroundColor: "#f44336",
    paddingVertical: 12,
    paddingHorizontal: 20,
    borderRadius: 10,
    width: "45%",
    alignItems: "center",
  },
  confirmButtonText: {
    color: "#fff",
    fontSize: 16,
    fontWeight: "bold",
  },
});

export default ReservationModal;
