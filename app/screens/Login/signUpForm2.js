import React, { useEffect, useState } from "react";
import {
  View,
  Text,
  StyleSheet,
  ActivityIndicator,
  Button,
  TouchableOpacity,
  ScrollView, // ScrollView 추가
} from "react-native";
import axios from "axios";
import { useNavigation } from "@react-navigation/native";

const ReservationDetails = ({ route }) => {
  const [reservationData, setReservationData] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigation = useNavigation();
  const { doctorId, reservationId } = route.params;

  const handleQRLoad = () => {
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
    <ScrollView style={styles.container}>
      {" "}
      {/* ScrollView로 감싸기 */}
      <View style={styles.headerContainer}>
        <Text style={styles.reservationDate}>
          {
            new Date(reservationData.reservationItem.reservationDate)
              .toLocaleDateString("ko-KR") // 한국 형식으로 설정
              .replace(/\./g, " ") // 점(.)을 슬래시(/)로 변경
          }
          {
            new Date(reservationData.reservationItem.reservationDate)
              .toLocaleString()
              .split(",")[1]
          }
        </Text>

        <Text style={styles.patientName}>
          환자: {reservationData.reservationItem.patientName}
        </Text>
        <Text style={styles.reservationDate}>
          희망 진료 시간:{" "}
          {
            new Date(reservationData.reservationItem.signupDate)
              .toLocaleDateString("ko-KR") // 한국 형식으로 설정
              .replace(/\./g, " ") // 점(.)을 슬래시(/)로 변경
          }
          {
            new Date(reservationData.reservationItem.signupDate)
              .toLocaleString()
              .split(",")[1]
          }
        </Text>
      </View>
      <View style={styles.headerContainer}>
        <Text style={styles.sectionTitle}>예약 신청자 세부 정보</Text>
        <View style={styles.detailsBox}>
          <Text style={styles.sectionTitle2}>
            신청자 명: {reservationData.reservationItem.patientName}
          </Text>
          <Text style={styles.sectionTitle2}>
            과목: {reservationData.noncontactDiagFormInfo.department}
          </Text>
          <Text style={styles.sectionTitle2}>
            진료 종류: {reservationData.noncontactDiagFormInfo.diagType}
          </Text>
        </View>
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
    </ScrollView>
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
    width: "100%", // 너비를 100%로 설정
    borderBottomWidth: 3, // 밑줄의 두께
    borderBottomColor: "#EBF2EA", // 밑줄의 색상
    paddingBottom: 10,
    margin: 0, // 모든 여백을 0으로 설정
    paddingLeft: 0, // 왼쪽 패딩을 0으로 설정 (필요시)
    paddingRight: 0, // 오른쪽 패딩을 0으로 설정 (필요시)
  },
  reservationDate: {
    fontSize: 13,
    marginBottom: 10,
  },
  patientName: {
    fontSize: 15,
    marginBottom: 5,
  },
  detailsContainer: {
    marginTop: 20,
  },
  detailsBox: {
    backgroundColor: "#F0F8F5", // Light greenish background
    padding: 15,
    borderRadius: 10,
    justifyContent: "center",
    width: "100%", // Full width
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 10,
    marginTop: 10,
  },
  sectionTitle2: {
    marginBottom: 5,
  },
  detailItem: {
    marginBottom: 20, // 아래쪽 여백을 늘려 박스를 더 크게 보이게 함
    paddingBottom: 50, // 아래쪽 패딩을 늘려 내부 공간을 확보
    paddingHorizontal: 15, // 좌우 패딩 추가
    borderWidth: 1,
    borderColor: "#d1d1d1",
    borderRadius: 5,
  },
  label: {
    fontSize: 16,
    marginBottom: 10,
  },
  checkboxLabel: {
    fontSize: 14,
    margin: 8,
    color: "gray", // 오타 수정: colot -> color
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
    marginTop: 20,
  },
  buttonText: {
    fontSize: 16,
    color: "white",
  },
  noDataText: {
    textAlign: "center",
    marginTop: 20,
    fontSize: 18,
  },
});

export default ReservationDetails;
