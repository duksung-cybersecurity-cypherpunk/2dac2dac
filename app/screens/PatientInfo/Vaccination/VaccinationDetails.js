import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  StyleSheet,
  Image,
} from "react-native";

export default function VaccinationDetails({ route }) {
  const { data } = route.params;
  const [item, setItem] = useState([]);
  const [vaccInfo, setVaccInfo] = useState([]);
  const [date, setDate] = useState('');

  const getDayOfWeek = () => {
    const daysOfWeek = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
    const today = new Date();
    return daysOfWeek[today.getDay()];
  };

  const fetchData = async () => {
    try {
      const response = await fetch(`http://203.252.213.209:8080/api/v1/healthList/vaccination/1/${data.vaccId}`); 
      const vaccination = await response.json();
      if (vaccination.data) { 
        setItem(vaccination.data.agencyInfo); 
        setVaccInfo(vaccination.data.vaccinationDetailInfo);
        if (vaccination.data.vaccinationDetailInfo.vaccDate) {
          const datePart = vaccination.data.vaccinationDetailInfo.vaccDate.split('T')[0];
          const modifiedDatePart = datePart.replace(/-/g, '. ');
          setDate(modifiedDatePart);
        } else {
          console.error('vaccDate is undefined');
        }
      } else {
        console.error('Vaccination data is undefined');
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
      <View style={styles.hospitalBlock}>
        <Text style={styles.timeText}>{date}</Text>
        <View style={styles.hospitalInfoContainer}>
          <Image
            source={require("../../../../assets/images/hospital/Hospital.png")}
            style={styles.hospitalImage}
          />
          <View style={[{padding: 10}]}>
            <Text style={styles.hospitalName}> {item.agencyName} </Text>
            <Text
              style={[styles.hospitalInfo, {width: 280}]}
              numberOfLines={1}
              ellipsizeMode="tail"
            > {item.agencyAddress}</Text>
            <View style={[styles.hospitalInfoContainer, {paddingTop: 3}]}>
              <Text style={[styles.timeText, {color: "#94C973"}]}> ㆍ오늘 예약  | </Text>
              <Text style={[styles.timeText, {color: "#D6D6D6"}]}> {getDayOfWeek()} ({item.agencyTodayOpenTime}~{item.agencyTodayCloseTime})</Text>
            </View>
          </View>
        </View>
      </View>
      <Text style={[styles.titleText, {padding: 20}]}>접종 정보</Text>
      <View style={styles.row}>
        <Text>백신</Text>
        <Text>{vaccInfo.vaccName}</Text>
      </View>
      <View style={styles.row}>
        <Text>백신 차수</Text>
        <Text>{vaccInfo.vaccSeries}</Text>
      </View>
      <View style={styles.row}>
        <Text>접종 일자</Text>
        <Text>{date}</Text>
      </View> 
      <View style={styles.vaccinInfo}> 
        <Image 
          source={require("../../../../assets/images/PatientInfo/VaccDone.png")}
        />
        <Text style={[styles.titleText, {color: "#5E9740"}, {padding: 15}]}>접종 완료!</Text>
        <Text style={styles.vaccText}>{vaccInfo.postVaccinationDays}일 경과</Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  screenContainer: {
    flex: 1,
    backgroundColor: "white",
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
    paddingTop: 10,
    paddingLeft: 20,
    paddingRight: 20,
  },
  vaccinInfo: {
    justifyContent: "center",
    alignItems: "center",
    paddingTop: 90,
  },
  vaccText: {
    fontSize: 16,
    color: "#A3A3A3"
  },
  hospitalBlock: {
    padding: 20,
    borderBlockColor: "#EBF2EA",
    borderBottomWidth: 9,
  },
  hospitalImage: {
    width: 80,
    height: 80,
    borderRadius: 5,
    marginTop: 10,
  },
  hospitalInfoContainer: {
    flexDirection: "row",
  },
  hospitalName: {
    fontSize: 20,
    fontWeight: "bold",
  },
  hospitalInfo: {
    fontSize: 15,
  },
  titleText: {
    fontSize: 24,
    fontWeight: "bold",
  },
  timeText: {
    fontSize: 14,
  },
  text: {
    fontSize: 17,
  },
});