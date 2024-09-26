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
import { useNavigation } from "@react-navigation/native"; // 추가: useNavigation 가져오기

const ReservationDetails = ({ route }) => {
  const [reservationData, setReservationData] = useState(null);
  const [loading, setLoading] = useState(true);

  const navigation = useNavigation(); // 추가: 네비게이션 훅 사용
  const { doctorId, reservationId } = route.params;

  useEffect(() => {
    const fetchReservationDetails = async () => {
      try {
        const response = await axios.get(
          `http://203.252.213.209:8080/api/v1/reservations/form/${doctorId}/${reservationId}`
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
      {/* 상단 예약 정보 섹션 */}
      <View style={styles.headerContainer}>
        <Text style={styles.reservationDate}>
          {new Date(
            reservationData.reservationItem.reservationDate
          ).toLocaleString()}
        </Text>
        <Text style={styles.patientName}>
          환자: {reservationData.reservationItem.patientName}
        </Text>
        <Text style={styles.requestTime}>
          희망 진료 시간: {reservationData.noncontactDiagFormInfo.desiredTime}
        </Text>
      </View>

      {/* 예약 세부 정보 섹션 */}
      <View style={styles.detailsContainer}>
        <Text style={styles.sectionTitle}>예약 신청자 세부 정보</Text>
        <View style={styles.detailItem}>
          <Text style={styles.label}>희망 과목</Text>
          <Text style={styles.value}>
            {reservationData.noncontactDiagFormInfo.department}
          </Text>
        </View>
        <View style={styles.detailItem}>
          <Text style={styles.label}>선호 방식</Text>
          <Text style={styles.value}>
            {reservationData.noncontactDiagFormInfo.preferredMethod}
          </Text>
        </View>
        {/* 더 많은 세부 정보를 여기에 추가 */}
      </View>

      {/* 각 섹션 구분선 */}
      <View style={styles.separator}></View>

      {/* 기타 정보 섹션 */}
      <View style={styles.additionalInfoContainer}>
        <Text style={styles.sectionTitle}>기타 정보</Text>
        <Text style={styles.additionalInfoText}>
          {reservationData.noncontactDiagFormInfo.additionalInformation}
        </Text>
      </View>

      {/* 뒤로 가기 버튼 */}
      <View style={styles.buttonContainer}>
        <TouchableOpacity
          style={styles.backButton}
          onPress={() => navigation.goBack()} // 뒤로 가기 버튼 클릭 시 이전 화면으로 이동
        >
          <Text style={styles.buttonText}>뒤로 가기</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
  },
  headerContainer: {
    backgroundColor: "#fff",
    padding: 16,
    marginVertical: 8,
    borderRadius: 8,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.2,
    shadowRadius: 1.5,
    elevation: 2,
    marginHorizontal: 16,
    marginTop: "30%",
  },
  reservationDate: {
    fontSize: 16,
    color: "#444",
    marginBottom: 8,
  },
  patientName: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 4,
  },
  requestTime: {
    fontSize: 16,
    color: "#333",
  },
  detailsContainer: {
    backgroundColor: "#fff",
    padding: 16,
    marginVertical: 8,
    borderRadius: 8,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.2,
    shadowRadius: 1.5,
    elevation: 2,
    marginHorizontal: 16,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 8,
  },
  detailItem: {
    flexDirection: "row",
    justifyContent: "space-between",
    marginBottom: 8,
  },
  label: {
    fontSize: 16,
    color: "#555",
  },
  value: {
    fontSize: 16,
    color: "#333",
    fontWeight: "500",
  },
  additionalInfoContainer: {
    backgroundColor: "#fff",
    padding: 16,
    marginVertical: 8,
    borderRadius: 8,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.2,
    shadowRadius: 1.5,
    elevation: 2,
    marginHorizontal: 16,
  },
  additionalInfoText: {
    fontSize: 16,
    color: "#333",
  },
  separator: {
    height: 1,
    backgroundColor: "#ddd",
    marginVertical: 16,
    marginHorizontal: 16,
  },
  noDataText: {
    fontSize: 16,
    color: "red",
    textAlign: "center",
    marginTop: 20,
  },
  buttonContainer: {
    alignItems: "center", // 가운데 정렬
    marginTop: 16,
    marginBottom: 16,
  },
  backButton: {
    backgroundColor: "#28a745", // 초록색 버튼
    paddingVertical: 12,
    paddingHorizontal: 32,
    borderRadius: 8,
    width: "80%", // 너비를 넓게 설정
    alignItems: "center", // 텍스트 가운데 정렬
  },
  buttonText: {
    fontSize: 16,
    color: "#fff",
    fontWeight: "bold",
  },
});

export default ReservationDetails;
