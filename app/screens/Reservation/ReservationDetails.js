import React, { useEffect, useState } from "react";
import { SafeAreaView } from "react-native";
import {
  View,
  Text,
  StyleSheet,
  ActivityIndicator,
  Button,
  TouchableOpacity,
  ScrollView,
} from "react-native";
import axios from "axios";
import dayjs from "dayjs";
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
    <SafeAreaView style={{ flex: 1 }}>
      <ScrollView
        style={styles.container}
        contentContainerStyle={{ justifyContent: "flex-start", flexGrow: 1 }}
      >
        <View>
          <View style={styles.headerContainer}>
            <Text style={styles.reservationDate}>
              {dayjs(reservationData.reservationItem.signupDate).format(
                "YYYY.MM.DD HH:mm"
              )}
            </Text>

            <Text style={styles.patientName}>
              환자: {reservationData.reservationItem.patientName}
            </Text>
            <Text style={styles.reservationDate}>
              희망 진료 시간:{" "}
              {dayjs(reservationData.reservationItem.signupDate).format(
                "YYYY.MM.DD HH:mm"
              )}
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
            <Text style={styles.label}>
              현재 복용 중인 약이 있다면 알려주세요.
            </Text>

            <View style={styles.checkboxContainer}>
              <View style={styles.checkbox}>
                {reservationData.noncontactDiagFormInfo.isPrescribedDrug && (
                  <Text style={styles.checkboxLabel}>✔</Text>
                )}
              </View>
              <Text style={styles.summary}>현재 복용 중인 약이 있습니다.</Text>
            </View>
            <View style={styles.detailItem}>
              <Text style={styles.value}>
                {reservationData.noncontactDiagFormInfo.isPrescribedDrug
                  ? reservationData.noncontactDiagFormInfo.prescribedDrug
                  : "없음"}
              </Text>
            </View>

            <Text style={styles.label}>
              알레르기 증상을 보인 적이 있다면 알려주세요.
            </Text>

            <View style={styles.checkboxContainer}>
              <View style={styles.checkbox}>
                {reservationData.noncontactDiagFormInfo.isAllergicSymptom && (
                  <Text style={styles.checkboxLabel}>✔</Text>
                )}
              </View>
              <Text style={styles.summary}>
                약이나 음식물로 인한 알레르기 혹은 그와 유사한 증상을 보인 적이
                있습니다.
              </Text>
            </View>
            <View style={styles.detailItem}>
              <Text style={styles.value}>
                {reservationData.noncontactDiagFormInfo.isAllergicSymptom
                  ? reservationData.noncontactDiagFormInfo.allergicSymptom
                  : "없음"}
              </Text>
            </View>

            <Text style={styles.label}>
              선천적 질환을 앓고 있다면 알려주세요.
            </Text>

            <View style={styles.checkboxContainer}>
              <View style={styles.checkbox}>
                {reservationData.noncontactDiagFormInfo.isPrescribedDrug && (
                  <Text style={styles.checkboxLabel}>✔</Text>
                )}
              </View>
              <Text style={styles.summary}>
                앓고 있는 선천적 질환이 있습니다.
              </Text>
            </View>

            <View style={styles.detailItem}>
              <Text style={styles.value}>
                {reservationData.noncontactDiagFormInfo.isInbornDisease
                  ? reservationData.noncontactDiagFormInfo.inbornDisease
                  : "없음"}
              </Text>
            </View>

            <Text style={styles.label}>
              기타 전달하고 싶은 정보가 있다면 알려주세요.
            </Text>
            <View style={styles.detailItem}>
              <Text style={styles.value}>
                {reservationData.noncontactDiagFormInfo.additionalInformation ||
                  "없음"}
              </Text>
            </View>
          </View>
          <View style={styles.buttonsRow}>
            <View style={styles.rejectButton}>
              <TouchableOpacity onPress={() => handleQRLoad()}>
                <Text style={styles.buttonText}>QR 보기</Text>
              </TouchableOpacity>
            </View>
            <View style={styles.acceptButton}>
              <TouchableOpacity onPress={() => navigation.goBack()}>
                <Text style={styles.buttonText}>뒤로 가기</Text>
              </TouchableOpacity>
            </View>
          </View>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: "#fff",
    padding: 16,
  },
  headerContainer: {
    backgroundColor: "white",
    width: "100%",
    borderBottomWidth: 3,
    borderBottomColor: "#EBF2EA",
    paddingBottom: 10,
    margin: 0,
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
    backgroundColor: "#F0F8F5",
    padding: 15,
    borderRadius: 10,
    justifyContent: "center",
    width: "100%",
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
    marginBottom: 20,
    paddingBottom: 50,
    paddingHorizontal: 15,
    borderWidth: 1,
    borderColor: "#d1d1d1",
    borderRadius: 5,
  },
  label: {
    fontSize: 16,
    marginBottom: 16,
  },
  summary: {
    fontSize: 14,
    color: "gray",
    marginBottom: 10,
  },
  value: {
    fontSize: 16,
    color: "gray",
    padding: 5,
  },
  rejectButton: {
    paddingVertical: 8,
    paddingHorizontal: 15,
    borderWidth: 1,
    borderColor: "#9BD394",
    borderRadius: 5,
    flex: 1, // Flex 1 to ensure it takes available space
    alignItems: "center",
    justifyContent: "center",
    marginBottom: 30,
    marginRight: 5,
  },
  acceptButton: {
    paddingVertical: 8,
    paddingHorizontal: 15,
    backgroundColor: "#9BD394",
    borderRadius: 5,
    flex: 1, // Flex 1 to ensure it takes available space
    alignItems: "center",
    justifyContent: "center",
    marginBottom: 30,
  },
  buttonsRow: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  buttonText: {
    fontSize: 16,
  },
  noDataText: {
    textAlign: "center",
    marginTop: 20,
    fontSize: 18,
  },
  checkboxContainer: {
    flexDirection: "row",
    alignItems: "center",
  },
  checkbox: {
    width: 20,
    height: 20,
    borderColor: "#d1d1d1",
    borderWidth: 1,
    borderRadius: 3,
    alignItems: "center",
    justifyContent: "center",
    marginRight: 8,
    marginBottom: 17,
  },
  checkboxLabel: {
    fontSize: 16,
  },
});

export default ReservationDetails;
