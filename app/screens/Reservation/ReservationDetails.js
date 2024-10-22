import React, { useEffect, useState } from "react";
import {
  View,
  Text,
  StyleSheet,
  ActivityIndicator,
  Button,
  TouchableOpacity,
} from "react-native";
import axios from "axios";
import { useNavigation } from "@react-navigation/native";
import CheckBox from "@react-native-community/checkbox";

const ReservationDetails = ({ route }) => {
  const [reservationData, setReservationData] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigation = useNavigation();
  const { doctorId, reservationId } = route.params;

  const handleQRLoad = () => {
    // 상위 네비게이터에서 PatientInfoStack으로 이동하고 QRLoad로 바로 이동
    navigation.navigate("PatientInfoStack", {
      id: 1,
      screen: "QRLoad",
      params: { doctorId, reservationId },
    });
  };

  useEffect(() => {
    const fetchReservationDetails = async () => {
      try {
        const response = await axios.get(
          `http://203.252.213.209:8080/api/v1/doctors/reservations/form/${doctorId}/${reservationId}`
        );
        setReservationData(response.data.data);
      } catch (error) {
        console.error("Failed to fetch reservation details:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchReservationDetails();
  }, [doctorId, reservationId]);

  if (loading) {
    return <ActivityIndicator size="large" color="#007BFF" />;
  }

  if (!reservationData) {
    return (
      <Text style={styles.noDataText}>No reservation data available.</Text>
    );
  }

  return (
    <View style={styles.container}>
      <View style={styles.headerContainer}>
        <Text style={styles.reservationDate}>
          {
            new Date(reservationData.reservationItem.reservationDate)
              .toLocaleString()
              .split(",")[0]
          }
        </Text>
        <Text style={styles.patientName}>
          환자: {reservationData.reservationItem.patientName}
        </Text>
        <Text style={styles.patientName}>
          희망 진료 시간:{" "}
          {
            new Date(reservationData.reservationItem.reservationDate)
              .toLocaleString()
              .split(",")[1]
          }
        </Text>
      </View>
      <Text style={styles.sectionTitle}>예약 신청자 세부 정보</Text>
      <View style={styles.detailsBox}>
        <Text>신청자 명: {reservationData.reservationItem.patientName}</Text>
        <Text>과목: {reservationData.noncontactDiagFormInfo.department}</Text>
        <Text>
          진료 종류: {reservationData.noncontactDiagFormInfo.diagType}
        </Text>
      </View>
      <View style={styles.detailsContainer}>
        <Text style={styles.label}>현재 복용 중인 약이 있습니다.</Text>

        <View style={styles.detailItem}>
          <Text style={styles.value}>
            {reservationData.noncontactDiagFormInfo.isPrescribedDrug
              ? reservationData.noncontactDiagFormInfo.prescribedDrug
              : "없음"}
          </Text>
        </View>

        {/* Display allergy information */}
        <Text style={styles.label}>알레르기 증상이 있습니다.</Text>
        <Text style={styles.checkboxLabel}>
          약이나 음식물로 인한 알레르기 혹은 그와 유사한 증상을 보인 적이
          있습니다.
        </Text>
        <View style={styles.detailItem}>
          <Text style={styles.value}>
            {reservationData.noncontactDiagFormInfo.isAllergicSymptom
              ? reservationData.noncontactDiagFormInfo.allergicSymptom
              : "없음"}
          </Text>
        </View>

        {/* Display congenital disease information */}
        <Text style={styles.label}>선천적 질환이 있습니다.</Text>
        <Text style={styles.checkboxLabel}>
          앓고 있는 선천적 질환이 있습니다.
        </Text>
        <View style={styles.detailItem}>
          <Text style={styles.value}>
            {reservationData.noncontactDiagFormInfo.isInbornDisease
              ? reservationData.noncontactDiagFormInfo.inbornDisease
              : "없음"}
          </Text>
        </View>

        {/* Display additional information */}
        <Text style={styles.label}>기타 정보가 있습니다.</Text>
        <View style={styles.detailItem}>
          <Text style={styles.value}>
            {reservationData.noncontactDiagFormInfo.additionalInformation ||
              "없음"}
          </Text>
        </View>
      </View>
      <View style={styles.buttonsRow}>
        <View style={styles.rejectButton}>
          <TouchableOpacity
            style={styles.backButton}
            onPress={() => handleQRLoad()}
          >
            <Text style={styles.buttonText}>QR 보기</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.acceptButton}>
          <TouchableOpacity
            style={styles.backButton}
            onPress={() => navigation.goBack()}
          >
            <Text style={styles.buttonText}>뒤로 가기</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    justifyContent: "flex-start",
    backgroundColor: "#fff",
    padding: 16,
  },
  headerContainer: {
    backgroundColor: "white",
    width: "100%",

    paddingBottom: 20,
  },
  reservationDate: {
    fontSize: 19,
    fontWeight: "bold",
    marginBottom: 10,
  },
  patientName: {
    fontSize: 16,
    marginBottom: 5,
  },
  detailsContainer: {
    marginTop: 20,
  },
  detailsBox: {
    backgroundColor: "#F0F8F5", // Light greenish background
    padding: 15,
    borderRadius: 10,
    marginBottom: 30,
    justifyContent: "center",
    width: "100%", // Full width
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 10,
  },
  detailItem: {
    marginBottom: 10,
    paddingBottom: 23,
    borderWidth: 1,
    borderColor: "#d1d1d1",
    borderRadius: 5,
  },
  label: {
    fontSize: 16,
    marginBottom: 5,
  },
  checkboxLabel: {
    fontSize: 14,
    margin: 8,
    colot: "gray",
  },
  value: {
    fontSize: 16,
    color: "gray",
  },
  additionalInfoText: {
    fontSize: 16,
    marginTop: 10,
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
  buttonsRow: {
    flexDirection: "row",
    justifyContent: "space-between",
  },

  buttonText: {
    fontSize: 16,
  },
});

export default ReservationDetails;
