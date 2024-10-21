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
import Svg, { Circle, Text as SvgText } from 'react-native-svg';
import AsyncStorage from "@react-native-async-storage/async-storage";

export default function MedicalHistory() {
  const navigation = useNavigation();
  const [doctorInfo, setDoctorInfo] = useState(null);
  const [cnt, setCnt] = useState();
  const [item, setitem] = useState([]); //done
  const [schedule, setSchedule] = useState([]); //schedule

  const [pay, setPay] = useState([]); //need pay
  const [modalVisible, setModalVisible] = useState(false);
  const [price, setPrice] = useState("");

  useEffect(() => {
    fetchData();
  }, []);

  const ModalPress = (item) => {
    setSelectedItem(item);
    setModalVisible(true);
  };

  const fetchData = async () => {
    try {
      const userInfo = await AsyncStorage.getItem("userInfo");
      const userData = JSON.parse(userInfo);
      //console.log("userId", userData, ReservationDate(selectedDate));
      setDoctorInfo(userData.id);

      const response = await fetch(`http://203.252.213.209:8080/api/v1/doctors/reservations/${doctorInfo}/today`);
      const data = await response.json();
      setitem(data.data.completedReservationItemList);
      setSchedule(data.data.scheduledReservationItemList);
      setPay(data.data.toBeCompleteReservationItemList);
      setCnt(data.data.totalCnt);
    } catch (error) {
      console.error('Error fetching data:', error);
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
      console.log(response.data);
      setModalVisible(false);
      setPrice("");
    } catch (error) {
      console.error("Error:", error);
    }
  };

  // const CircularProgress = ({ percent }) => {
  //   const radius = 150;
  //   const circumference = 2 * Math.PI * radius;
  //   const strokeDashoffset = circumference - (percent / 100) * circumference;
  
  //   return (
  //     <View style={styles.progressContainer}>
  //       <Svg height={radius * 2} width={radius * 2}>
  //         <Circle
  //           stroke="#CFECD9" // 배경 원
  //           fill="none"
  //           strokeWidth={8}
  //           r={radius}
  //           cx={radius}
  //           cy={radius}
  //         />
  //         <Circle
  //           stroke="#47743A" // 진행 원
  //           fill="none"
  //           strokeWidth={8}
  //           r={radius}
  //           cx={radius}
  //           cy={radius}
  //           strokeDasharray={circumference}
  //           strokeDashoffset={strokeDashoffset}
  //           rotation="-90"
  //           origin={`${radius}, ${radius}`}
  //         />
  //         <SvgText
  //           x="50%"
  //           y="50%"
  //           textAnchor="middle"
  //           fontSize="14"
  //           fill="#555"
  //           alignmentBaseline="middle"
  //           dy=".3em"
  //         >
  //           다음 진료까지 앞으로
  //         </SvgText>
  //         <SvgText
  //           x="50%"
  //           y="50%"
  //           textAnchor="middle"
  //           fontSize="40"
  //           fontWeight="bold"
  //           fill="#000"
  //           alignmentBaseline="middle"
  //           dy="1.2em"
  //         >
  //           01 : 01 : 01
  //         </SvgText>
  //         <SvgText
  //           x="50%"
  //           y="50%"
  //           textAnchor="middle"
  //           fontSize="12"
  //           fill="#000"
  //           alignmentBaseline="middle"
  //           dy="2em"
  //         >
  //           2024년 3월 17일 일요일 오후 16:00
  //         </SvgText>
  //       </Svg>
  //     </View>
  //   );
  // };
  
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
              <Image source={require("../../assets/images/PatientInfo/ListNonExist.png")} />
              <Text style={[styles.hospitalName, { paddingTop: 20, paddingBottom: 10 }]}>
                오늘 진료 예약 내역이 없어요.
              </Text>
            </View>
          ) : (
            <ScrollView style={styles.scrollView}>
              {/* 완료되지 않은 예약 항목 리스트 랜더링 */}
              {pay && pay.length > 0 ? (
                pay.map((item) => (
                <View key={item.noncontactDiagId} style={{ height: "100%", backgroundColor: "white" }}>
                  <View style={styles.screenContainer}>
                    <View style={styles.row}>
                      <ScrollView style={styles.scrollView}>
                        <View style={styles.hospitalBlock}>
                          <View style={{ flex: 1 }}>
                            <Text style={styles.timeText}>{item.reservationDate}</Text>
                            <View style={styles.hospitalInfoContainer}>
                              <Text style={styles.hospitalName}>{item.patientName} 환자</Text>
                            </View>

                            <TouchableOpacity
                              style={styles.prescriptionBlock}
                              onPress={() => ModalPress(item)}
                              activeOpacity={0.7}
                            >
                              <Text style={styles.prescriptionText}>진료비 청구하기</Text>
                            </TouchableOpacity>
                          </View>
                        </View>
                      </ScrollView>
                    </View>
                  </View>
                </View>
              ))) : (
                <Text></Text>
              )}
              {schedule && schedule.length > 0 ? (
                schedule.map((item) => (
                <View key={item.noncontactDiagId} style={{ height: "100%", backgroundColor: "white" }}>
                  <View style={styles.screenContainer}>
                    <View style={styles.row}>
                      <ScrollView style={styles.scrollView}>
                        <View style={styles.hospitalBlock}>
                          <View style={{ flex: 1 }}>
                            <Text style={styles.timeText}>{schedule.reservationDate}</Text>
                            <View style={styles.hospitalInfoContainer}>
                              <Text style={styles.hospitalName}>{schedule.patientName} 환자</Text>
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
              ))) : (
                <Text></Text>
              )}
                {/* 완료된 예약 항목 리스트 랜더링 */}
                {item && item.length > 0 ? (
                  item.map((item) => (
                  <View key={item.noncontactDiagId} style={{ height: "100%", backgroundColor: "white" }}>
                    <View style={styles.screenContainer}>
                      <View style={styles.row}>
                        <ScrollView style={styles.scrollView}>
                          <View style={styles.hospitalBlock}>
                            <View style={{ flex: 1 }}>
                              <Text style={styles.timeText}>{item.reservationDate}</Text>
                              <View style={styles.hospitalInfoContainer}>
                                <Text style={styles.hospitalName}>{item.patientName} 환자</Text>
                              </View>

                              <View style={styles.vaccinInfo}>
                                <Image 
                                  source={require("../../assets/images/PatientInfo/vaccCert.png")}
                                  style={styles.vaccCert}
                                />
                                <Text style={[styles.vaccText, { color: "#737373" }]}> 진료가 완료되었습니다.</Text>
                              </View>
                            </View>
                          </View>
                        </ScrollView>
                      </View>
                    </View>
                  </View>
                ))) : (
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
        <View style={{ flex: 1, justifyContent: "center", alignItems: "center", backgroundColor: "rgba(0, 0, 0, 0.5)" }}>
          <View style={{ width: 300, padding: 20, backgroundColor: "white", borderRadius: 10 }}>
            <Text style={{ marginBottom: 15 }}>진료비 금액 입력</Text>
            <TextInput
              style={{ height: 40, borderColor: 'gray', borderWidth: 1, marginBottom: 15, paddingLeft: 10 }}
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
    justifyContent: 'center',
    alignItems: 'center',
  },
});