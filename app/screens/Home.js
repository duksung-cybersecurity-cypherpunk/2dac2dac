import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Image,
  TextInput,
  Modal,
  Button,
} from "react-native";
import { useNavigation, useRoute } from "@react-navigation/native";
import axios from "axios";
import Svg, { Circle, Text as SvgText } from "react-native-svg";
import AsyncStorage from "@react-native-async-storage/async-storage";

export default function Home() {
  const navigation = useNavigation();
  const [doctorInfo, setDoctorInfo] = useState(null);
  const [cnt, setCnt] = useState();
  const [completed, setCompleted] = useState([]); //done
  const [schedule, setSchedule] = useState([]); //schedule
  const [pay, setPay] = useState([]); //need pay

  const [modalVisible, setModalVisible] = useState(false);
  const [price, setPrice] = useState("");

    useEffect(() => {
      const fetchUserInfo = async () => {
        try {
          const userInfo = await AsyncStorage.getItem("userInfo");
          const userData = JSON.parse(userInfo);
          setDoctorInfo(userData.id);
        } catch (error) {
          console.error("Error fetching user info:", error);
        }
      };

      fetchUserInfo();
    }, []); // doctorInfo를 설정하는 부분은 useEffect에서 한 번만 호출

    useEffect(() => {
      if (doctorInfo) {
        fetchData(); // doctorInfo가 설정된 후에만 fetchData 호출
      }
    }, [doctorInfo]); // doctorInfo가 변경될 때만 이 useEffect 실행

  const ModalPress = (item) => {
    setSelectedItem(item);
    setModalVisible(true);
  };

  const fetchData = async () => {
    try {
      const response = await fetch(
        `http://203.252.213.209:8080/api/v1/doctors/reservations/${doctorInfo}/today`
      );
      const data = await response.json();
      console.log(data);
      setCompleted(data.data.completedReservationItemList);
      setSchedule(data.data.scheduledReservationItemList[0]);
      setPay(data.data.toBeCompleteReservationItemList);
      setCnt(data.data.totalCnt);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const handleSubmit = async () => {
    try {
      const response = await axios.post(
        `http://203.252.213.209:8080/api/v1/doctors/reservations/complete/${item.doctorId}/${item.noncontactDiagId}`,
        {
          doctorId: item.doctorId, // 의사 ID
          reservationId: item.noncontactDiagId, // 예약 ID
          price: price, // 입력받은 금액
        }
      );

      setModalVisible(false);
      setPrice("");
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <View style={{ height: "100%", backgroundColor: "white" }}>
      <View style={styles.screenContainer}>
        <View style={styles.upperBlock}>
          <View style={styles.labelContainer}>
            <Text style={styles.label}>DOC'TECH </Text>
          </View>
          {/* <CircularProgress percent={20} /> */}
        </View>
      </View>
      <View style={styles.screenContainer}>
        <View style={styles.lowerBlock}>
          <View style={styles.row}>
            {cnt === 0 ? (
              <View style={{ alignItems: "center", paddingTop: 250 }}>
                <Image
                  source={require("../../assets/images/PatientInfo/ListNonExist.png")}
                />
                <Text
                  style={[
                    styles.hospitalName,
                    { paddingTop: 20, paddingBottom: 10 },
                  ]}
                >
                  오늘 진료 예약 내역이 없어요.
                </Text>
              </View>
            ) : (
              <ScrollView style={styles.scrollView}>
                {/* 완료되지 않은 예약 항목 리스트 랜더링 */}
                {pay && pay.length > 0 ? (
                  pay.map((item) => (
                    <View
                      key={item.reservationId}
                      style={{ height: "100%", backgroundColor: "white" }}
                    >
                      <View style={styles.screenContainer}>
                        <View style={styles.row}>
                          <ScrollView style={styles.scrollView}>
                            <View style={styles.hospitalBlock}>
                              <View style={{ flex: 1 }}>
                                <Text style={styles.timeText}>
                                  {item.reservationDate}
                                </Text>
                                <View style={styles.hospitalInfoContainer}>
                                  <Text style={styles.hospitalName}>
                                    {item.patientName} 환자
                                  </Text>
                                </View>

                                <TouchableOpacity
                                  style={styles.prescriptionBlock}
                                  onPress={() => ModalPress(item)}
                                  activeOpacity={0.7}
                                >
                                  <Text style={styles.prescriptionText}>
                                    진료비 청구하기
                                  </Text>
                                </TouchableOpacity>
                              </View>
                            </View>
                          </ScrollView>
                        </View>
                      </View>
                    </View>
                  ))
                ) : (
                  <Text></Text>
                )}
                {schedule && schedule.length > 0 ? (
                  schedule.map((item) => (
                    <View
                      key={item.reservationId}
                      style={{ height: "100%", backgroundColor: "white" }}
                    >
                      <View style={styles.screenContainer}>
                        <View style={styles.row}>
                          <ScrollView style={styles.scrollView}>
                            <View style={styles.hospitalBlock}>
                              <View style={{ flex: 1 }}>
                                <Text style={styles.timeText}>
                                  {item.reservationDate}
                                </Text>
                                <View style={styles.hospitalInfoContainer}>
                                  <Text style={styles.hospitalName}>
                                    {item.patientName} 환자
                                  </Text>
                                </View>
                                {/* <TouchableOpacity
                              style={styles.prescriptionBlock}
                              onPress={() => ModalPress(item)}
                              activeOpacity={0.7}
                            >
                              <Text style={styles.prescriptionText}>진료비 청구하기</Text>
                            </TouchableOpacity> */}
                              </View>
                            </View>
                          </ScrollView>
                        </View>
                      </View>
                    </View>
                  ))
                ) : (
                  <Text></Text>
                )}
                {/* 완료된 예약 항목 리스트 랜더링 */}
                {completed && completed.length > 0 ? (
                  completed.map((item) => (
                    <View
                      key={item.reservationId}
                      style={{ height: "100%", backgroundColor: "white" }}
                    >
                      <View style={styles.screenContainer}>
                        <View style={styles.row}>
                          <ScrollView style={styles.scrollView}>
                            <View style={styles.hospitalBlock}>
                              <View style={{ flex: 1 }}>
                                <Text style={styles.timeText}>
                                  {item.reservationDate}
                                </Text>
                                <View style={styles.hospitalInfoContainer}>
                                  <Text style={styles.hospitalName}>
                                    {item.patientName} 환자
                                  </Text>
                                </View>

                                <View style={styles.vaccinInfo}>
                                  <Image
                                    source={require("../../assets/images/PatientInfo/vaccCert.png")}
                                    style={styles.vaccCert}
                                  />
                                  <Text
                                    style={[
                                      styles.vaccText,
                                      { color: "#737373" },
                                    ]}
                                  >
                                    {" "}
                                    진료가 완료되었습니다.
                                  </Text>
                                </View>
                              </View>
                            </View>
                          </ScrollView>
                        </View>
                      </View>
                    </View>
                  ))
                ) : (
                  <Text></Text>
                )}
              </ScrollView>
            )}
          </View>
        </View>
      </View>
      {/* 모달 팝업 */}
      <Modal
        animationType="slide"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => {
          setModalVisible(!modalVisible);
        }}
      >
        <View
          style={{
            flex: 1,
            justifyContent: "center",
            alignItems: "center",
            backgroundColor: "rgba(0, 0, 0, 0.5)",
          }}
        >
          <View
            style={{
              width: 300,
              padding: 20,
              backgroundColor: "white",
              borderRadius: 10,
            }}
          >
            <Text style={{ marginBottom: 15 }}>진료비 금액 입력</Text>
            <TextInput
              style={{
                height: 40,
                borderColor: "gray",
                borderWidth: 1,
                marginBottom: 15,
                paddingLeft: 10,
              }}
              placeholder="금액을 입력하세요"
              keyboardType="numeric"
              value={price}
              onChangeText={setPrice}
            />
            <Button title="청구하기" onPress={handleSubmit} />
            <Button title="취소" onPress={() => setModalVisible(false)} />
          </View>
        </View>
      </Modal>
    </View>
  );
}

const styles = StyleSheet.create({
  screenContainer: {
    flexDirection: "row",
    backgroundColor: "white",
    alignItems: "center",
    justifyContent: "center",
  },
  blocks: {
    width: "49%", // Adjust as needed to fit your design
    height: "40%", // 너비와 높이 비율 유지
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#9BD394",
    borderRadius: 6,
    marginTop: 20,
    marginRight: 8,
  },
  one: {
    paddingTop: 40,
    paddingRight: 50,
    paddingLeft: 50,
    backgroundColor: "white",
  },
  row: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  scrollView: {
    flex: 1,
    width: "100%",
  },
  vaccinInfo: {
    height: 40,
    width: "100%",
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#EBF2EA",
    borderRadius: 6,
    marginTop: 15,
    marginBottom: 15,
  },
  vaccCert: {
    width: 30,
    height: 30,
  },
  vaccText: {
    fontSize: 16,
  },
  hospitalBlock: {
    flexDirection: "row",
    height: 180,
    backgroundColor: "white",
    padding: 20,
    borderBottomColor: "#D6D6D6",
    borderBottomWidth: 1,
  },
  hospitalImage: {
    width: 80,
    height: 80,
    borderRadius: 5,
    marginTop: 10,
  },
  hospitalInfoContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  hospitalName: {
    fontSize: 20,
    fontWeight: "bold",
    paddingTop: 5,
  },
  hospitalInfo: {
    fontSize: 13,
    color: "#A3A3A3",
    paddingTop: 5,
  },
  prescriptionBlock: {
    width: "100%",
    height: 50,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#9BD394",
    borderRadius: 8,
    marginTop: 15,
  },
  prescriptionText: {
    fontSize: 16,
  },
  timeText: {
    fontSize: 14,
  },
  text: {
    fontSize: 17,
  },
  progressContainer: {
    justifyContent: "center",
    alignItems: "center",
  },
});