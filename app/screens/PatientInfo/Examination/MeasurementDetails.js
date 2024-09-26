import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
} from "react-native";
import { useNavigation } from "@react-navigation/native";

export default function MeasurementDetails({ route }) {
  const { data } = route.params;
  const navigation = useNavigation();
  const [item, setItem] = useState([]);
  const [date, setDate] = useState('');

  const handleBlockPress = (id, data) => {
    navigation.navigate("ExaminationInfoStack", { id, data }); 
  };

  const fetchData = async () => {
    try {
      const response = await fetch(`http://203.252.213.209:8080/api/v1/healthList/healthScreening/1/${data.hsId}`); 
      const Measurement = await response.json();
      if (Measurement.data) { 
        setItem(Measurement.data.measurementTestInfo);
        console.log(item);
        if (data.diagDate) {
          const datePart = data.diagDate.split('T')[0];
          const modifiedDatePart = datePart.replace(/-/g, '. ');
          setDate(modifiedDatePart);
        } else {
          console.error('ExamDate is undefined');
        }
      } else {
        console.error('Measurement data is undefined');
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };
  
  useEffect(() => {
    fetchData();
  }, []); 

  const calculatePosition = (waist) => {
    if (waist <= 50) {
      return 0; // 50 이하일 경우 0%
    } else if (waist > 50 && waist <= 85) {
      return ((waist - 50) / (85 - 50)) * 50; // 50~85 구간을 0%~50%로 매핑
    } else if (waist > 85 && waist < 120) {
      return 50 + ((waist - 85) / (120 - 85)) * 50; // 85~120 구간을 50%~100%로 매핑
    } else {
      return 100; // 120 이상일 경우 100%
    }
  };
  const calculateBmiPosition = (bmi) => {
    if (bmi <= 10) {
      return 0; // 10 이하일 경우 저체중 바의 0%
    } else if (bmi > 10 && bmi <= 18.5) {
      return ((bmi - 10) / (18.5 - 10)) * 25; // 10~18.5 범위를 0%~25%로 매핑
    } else if (bmi > 18.5 && bmi <= 25.0) {
      return 25 + ((bmi - 18.5) / (25.0 - 18.5)) * 25; // 18.5~25.0 범위를 25%~50%로 매핑
    } else if (bmi > 25.0 && bmi <= 30.0) {
      return 50 + ((bmi - 25.0) / (30.0 - 25.0)) * 25; // 25.0~30.0 범위를 50%~75%로 매핑
    } else if (bmi > 30.0 && bmi < 40.0) {
      return 75 + ((bmi - 30.0) / (40.0 - 30.0)) * 25; // 30.0~40.0 범위를 75%~100%로 매핑
    } else {
      return 100; // 40.0 이상일 경우 비만 바의 끝
    }
  };
  const calculateBloodPressurePosition = (bloodPressureHigh) => {
    if (bloodPressureHigh <= 80) {
      return 0; // 80 이하일 경우 정상 바의 0%
    } else if (bloodPressureHigh > 80 && bloodPressureHigh <= 120) {
      return ((bloodPressureHigh - 80) / (120 - 80)) * 33.3; // 80~120 범위를 0%~33.3%로 매핑
    } else if (bloodPressureHigh > 120 && bloodPressureHigh < 140) {
      return 33.3 + ((bloodPressureHigh - 120) / (140 - 120)) * 33.3; // 120~140 범위를 33.3%~66.6%로 매핑
    } else if (bloodPressureHigh >= 140 && bloodPressureHigh < 160) {
      return 66.6 + ((bloodPressureHigh - 140) / (160 - 140)) * 33.3; // 140~160 범위를 66.6%~100%로 매핑
    } else {
      return 100; // 160 이상일 경우 고혈압 전 단계 바의 끝
    }
  };
  

  return (
    <View style={styles.screenContainer}>
        <View style={styles.hospitalBlock}>
            <View style={{ flex: 1 }}>
                <Text>{date}</Text>
                <Text style={styles.hospitalName}>{data.doctorName} 의사</Text>
                <Text style={styles.hospitalInfo}>{data.doctorHospital}</Text>
            </View>
        </View>
        <Text style={styles.titleText}>건강 검진 결과</Text>
        <Text style={styles.subTitleText}>계측 검사</Text>
          <ScrollView style={[{paddingLeft: 20, paddingRight: 20}]}>
            <View style={styles.row}>
                <Text>신장 (cm)</Text>
                <Text>{item.height}</Text>
            </View>
            <View style={styles.row}>
                <Text>체중 (kg)</Text>
                <Text>{item.weight}</Text>
            </View>
            <View style={styles.scrollView}>
              <Text>허리 둘레 (cm)</Text>
              <Text style={[styles.valueBox, {width: "10%"}, {left: `${calculatePosition(item.waist)-4}%`}]}>{item.waist}</Text>
              <Text style={[{left: `${calculatePosition(item.waist)}%`}]}>|</Text>
              <View style={[{flexDirection: "row"}]}>
                {/* 정상 구간 */}
                <View style={styles.waistLeftBar}>
                  <Text>정상</Text>
                </View>
                {/* 복부 비만 구간 */}
                <View style={styles.waistRightBar}>
                  <Text>복부 비만</Text>
                </View>
              </View>
              <Text style={styles.waistmidValueText}>85</Text>
            </View>
            <View style={styles.scrollView}>
              <Text>체질량 지수</Text>
              <Text style={[styles.valueBox, {width: "13%"}, {left: `${calculateBmiPosition(item.bmi)-6}%`}]}>{item.bmi}</Text>
              <Text style={{ left: `${calculateBmiPosition(item.bmi)}%` }}>|</Text>
              <View style={[{flexDirection: "row"}]}>
                {/* 저체중 구간 */}
                <View style={styles.bmiBar1}>
                  <Text>저체중</Text>
                </View>
                {/* 정상 구간 */}
                <View style={[styles.bmiBar2, {backgroundColor: 'rgba(118, 185, 71, 0.5)'}]}>
                  <Text>정상</Text>
                </View>
                {/* 과체중 구간 */}
                <View style={[styles.bmiBar2, {backgroundColor: 'rgba(71, 116, 58, 0.5)'}]}>
                  <Text>과체중</Text>
                </View>
                {/* 비만 구간 */}
                <View style={styles.bmiBar3}>
                  <Text>비만</Text>
                </View>
              </View>
              <View style={[{flexDirection: "row"}]}>
                <Text style={styles.bmiFirstValueText}>18.5</Text>
                <Text style={styles.bmiMidValueText}>25.0</Text>
                <Text style={styles.bmiFinValueText}>30.0</Text>
              </View>
            </View>
            <View style={styles.row}>
                <Text>시력</Text>
                <Text>좌 {item.sightLeft} / 우 {item.sightRight}</Text>
            </View>
            <View style={styles.row}>
                <Text>청력</Text>
                <Text>좌 {item.hearingLeft} / 우 {item.hearingRight}</Text>
            </View>
            <View style={styles.scrollView}>
              <Text>혈압 (mmHg)</Text>
              <Text style={[styles.valueBox, {width: "18%"}, {left: `${calculateBloodPressurePosition(item.bloodPressureHigh)-8}%`}]}>{item.bloodPressureHigh}/{item.bloodPressureLow}</Text>
              <Text style={{ left: `${calculateBloodPressurePosition(item.bloodPressureHigh)}%` }}>|</Text>
              <View style={[{flexDirection: "row"}]}>
                {/* 정상 구간 */}
                <View style={styles.bloodbar1}>
                  <Text>정상</Text>
                </View>
                {/* 고혈압 전 단계 구간 */}
                <View style={styles.bloodbar2}>
                  <Text>고혈압 전 단계</Text>
                </View>
                {/* 고혈압 의심 구간 */}
                <View style={styles.bloodbar3}>
                  <Text>고혈압 의심</Text>
                </View>
              </View>
              <View style={[{flexDirection: "row"}]}>
                <Text style={styles.bloodFirstValueText}> 120/80 </Text>
                <Text style={styles.bloodFinValueText}> 140/90 </Text>
              </View>
            </View>
        <View style={styles.row}>
            <TouchableOpacity 
                style={styles.buttonBack}
                onPress={() => handleBlockPress(1, data)}
                activeOpacity={0.7}
            >
                <Text style={styles.buttonText}>뒤로 가기</Text>
            </TouchableOpacity>
            <TouchableOpacity 
                style={styles.button} 
                onPress={() => handleBlockPress(3, data)}
                activeOpacity={0.7}
            >
                <Text style={styles.buttonText}>혈액 검사 결과 보기</Text>
            </TouchableOpacity>
        </View>
        </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  screenContainer: {
    flex: 1,
    backgroundColor: "white",
  },
  scrollView: {
    flex: 1,
  },
  row: {
    flexDirection: "row",
    justifyContent: "space-between",
    paddingBottom: 5,
  },
  healthScreeningBlock: {
    paddingBottom: 10,
    paddingLeft: 20,
    paddingRight: 20,
  },
  opinionBlock: {
    backgroundColor: "#EBF2EA",
    borderRadius: 10,
    padding: 20,
    marginHorizontal: 20,
  },
  hospitalBlock: {
    flexDirection: "row",
    height: 125,
    padding: 20,
    borderBottomColor: "#EBF2EA",
    borderBottomWidth: 9,
  },
  hospitalName: {
    fontSize: 20,
    fontWeight: "bold",
    paddingTop: 5,
    paddingBottom: 5,
  },
  hospitalInfo: {
    fontSize: 14,
    color: "#A3A3A3",
  },
  titleText: {
    fontSize: 24,
    fontWeight: "bold",
    padding: 20,
  },
  subTitleText: {
    fontSize: 18,
    fontWeight: "bold",
    paddingBottom: 10,
    paddingLeft: 20,
  },
  grayText: {
    color: "#737373",
  },

  waistLeftBar: {
    alignItems: 'center',
    justifyContent: "center",
    width: '50%',
    height: 30,
    backgroundColor: 'rgba(118, 185, 71, 0.5)',
    borderTopLeftRadius: 6,
    borderBottomLeftRadius: 6,
  },
  waistRightBar: {
    alignItems: 'center',
    justifyContent: "center",
    width: '50%',
    height: 30,
    backgroundColor: 'rgba(82, 82, 82, 0.5)',
    borderTopRightRadius: 6,
    borderBottomRightRadius: 6,
  },
  waistmidValueText: {
    left: '48%',
    marginTop: 5,
    marginBottom: 20,
  },
  bmiBar1: {
    alignItems: 'center',
    justifyContent: "center", 
    width: '25%',
    height: 30,
    backgroundColor: 'rgba(155, 211, 148, 0.5)',
    borderTopLeftRadius: 6,
    borderBottomLeftRadius: 6,
  },
  bmiBar2: {
    alignItems: 'center',
    justifyContent: "center",
    width: '25%',
    height: 30,
  },
  bmiBar3: {
    alignItems: 'center',
    justifyContent: "center",
    width: '25%',
    height: 30,
    backgroundColor: 'rgba(82, 82, 82, 0.5)',
    borderTopRightRadius: 6,
    borderBottomRightRadius: 6,
  },
  bmiFirstValueText: {
    left: '180%',
    marginTop: 5,
    marginBottom: 20,
  },
  bmiMidValueText: {
    left: '320%',
    marginTop: 5,
    marginBottom: 20,
  },
  bmiFinValueText: {
    left: '480%',
    marginTop: 5,
    marginBottom: 20,
  },
  bloodbar1: {
    alignItems: 'center',
    justifyContent: "center", 
    width: '33.3%',
    height: 30,
    backgroundColor: 'rgba(155, 211, 148, 0.5)',
    borderTopLeftRadius: 6,
    borderBottomLeftRadius: 6,
  },
  bloodbar2: {
    alignItems: 'center',
    justifyContent: "center",
    width: '33.3%',
    height: 30,
    backgroundColor: 'rgba(71, 116, 58, 0.5)',
  },
  bloodbar3: {
    alignItems: 'center',
    justifyContent: "center",
    width: '33.3%',
    height: 30,
    backgroundColor: 'rgba(82, 82, 82, 0.5)',
    borderTopRightRadius: 6,
    borderBottomRightRadius: 6,
  },
  bloodFirstValueText: {
    left: '220%',
    marginTop: 5,
    marginBottom: 20,
  },
  bloodFinValueText: {
    left: '380%',
    marginTop: 5,
    marginBottom: 20,
  },
  valueBox: {
    backgroundColor: '#F5F5F5',
    borderRadius: 8,
    paddingLeft: 10,
    marginTop: 10,
  },
  buttonBack: {
    width: "30%",
    height: "60%",
    borderColor: '#9BD394',
    borderWidth: 2,
    borderRadius: 8,
    paddingTop: 8,
    alignItems: 'center',
    marginTop: 20,
    marginLeft: 20,
  },
  button: {
    width: "55%",
    height: "60%",
    backgroundColor: '#9BD394',
    borderRadius: 8,
    paddingTop: 10,
    alignItems: 'center',
    margin: 20,
  },
  buttonText: {
    fontSize: 17,
  },
  text: {
    fontSize: 17,
    fontWeight: "bold",
    padding: 20,
  },
});
