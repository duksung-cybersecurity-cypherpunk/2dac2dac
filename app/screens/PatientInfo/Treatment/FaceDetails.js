import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  StyleSheet,
  Image,
} from "react-native";

export default function FaceDetails({ route }) {
  const { data } = route.params;
  const [agencyInfo, setAgencyInfo] = useState([]);
  const [diagInfo, setDiagInfo] = useState([]);
  
  const getDayOfWeek = () => {
    const daysOfWeek = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
    const today = new Date();
    return daysOfWeek[today.getDay()];
  };

  const fetchData = async () => {
    try {
      const response = await fetch(`http://203.252.213.209:8080/api/v1/healthList/diagnosis/contact/1/${data}`); 
      const treatment = await response.json();
      if (treatment.data) {
        setAgencyInfo(treatment.data.agencyInfo);
        console.log(agencyInfo);
        setDiagInfo(treatment.data.diagDetailInfo);
        console.log(diagInfo); 
      } else {
        console.error('Contact treatment data is undefined');
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };
  
  useEffect(() => {
    fetchData();  
  }, []);

  return (
    <View style={styles.screenContainer}>
      <View style={{ padding: 20 }}>
        <Text style={styles.timeText}>{agencyInfo.diagDate}</Text>
        <View style={[styles.hospitalInfoContainer, { paddingBottom: 15 }]}>
        <Image source={require("../../../../assets/images/hospital/hospital_all.png")} style={styles.hospitalImage} />
          <View style={{ padding: 10 }}>
            <Text style={styles.hospitalName}>{agencyInfo.agencyName}</Text>
            <View style={{ padding: 3 }}></View>
            <Text
              style={[styles.hospitalInfo, {width: 260}]}
              numberOfLines={1}
              ellipsizeMode="tail"
            >
              {agencyInfo.agencyAddress}
            </Text>
            <View style={[{ flexDirection: "row" }, { padding: 3 }]}>
              {
                agencyInfo.agencyIsOpenNow === true ? (
                  <Text style={[styles.text, { color: "#94C973" }]}>ㆍ영업 중 | </Text>
                ) :
                  <Text style={[styles.text, { color: "#D6D6D6" }]}>ㆍ영업 휴무 | </Text>
              }
              <Text style={[styles.text, { color: "#D6D6D6" }]}> {getDayOfWeek()} ({agencyInfo.agencyTodayOpenTime}{" ~ "}{agencyInfo.agencyTodayCloseTime}) </Text>
            </View>
          </View>
        </View>
        <Text style={styles.titleText}>진료 세부 정보</Text>
        <View style={{ paddingTop: 20 }}>
          <View style={styles.row}>
            <Text>진료 형태</Text>
            <Text>{diagInfo.diagType}</Text>
          </View>
          <View style={[styles.row, { paddingTop: 15 }]}>
            <View style={styles.blockTop}>
              <Text>방문 / 입원일 수</Text>
            </View>
            <View style={styles.blockTop}>
              <Text>투약 / 요양 횟수</Text>
            </View>
            <View style={styles.blockTop}>
              <Text>처방 횟수</Text>
            </View>
          </View>
          <View style={styles.row}>
            <View style={styles.blockBottom}>
              <Text>{diagInfo.visit_days}일</Text>
            </View>
            <View style={styles.blockBottom}>
              <Text>{diagInfo.medication_cnt}일</Text>
            </View>
            <View style={styles.blockBottom}>
              <Text>{diagInfo.prescription_cnt}회</Text>
            </View>
          </View>
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  screenContainer: {
    flex: 1,
    backgroundColor: "white",
  },
  row: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  blockTop: {
    alignItems: 'center',
    justifyContent: 'center',
    width: "32%",
    height: 35,
    borderTopLeftRadius: 5,
    borderTopRightRadius: 5,
    backgroundColor: "#9BD394",
  },
  blockBottom: {
    alignItems: 'center',
    justifyContent: 'center',
    width: "32%",
    height: 35,
    borderBottomLeftRadius: 5,
    borderBottomRightRadius: 5,
    backgroundColor: "#EBF2EA",
  },
  hospitalImage: {
    width: 80,
    height: 80,
    borderRadius: 5,
    marginTop: 15,
  },
  hospitalInfoContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  hospitalName: {
    fontSize: 20,
    fontWeight: "bold",
  },
  hospitalInfo: {
    fontSize: 15,
  },
  timeText: {
    fontSize: 16,
  },
  titleText: {
    fontSize: 24,
    fontWeight: "bold",
  },
  text: {
    fontSize: 14,
  },
});
