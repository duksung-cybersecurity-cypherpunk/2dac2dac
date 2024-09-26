import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
} from "react-native";
import { useNavigation } from "@react-navigation/native";

export default function BloodDetails({ route }) {
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
        setItem(Measurement.data.bloodTestInfo);
        console.log(item);
        if (data.diagDate) {
          const datePart = data.diagDate.split('T')[0];
          const modifiedDatePart = datePart.replace(/-/g, '. ');
          setDate(modifiedDatePart);
        } else {
          console.error('ExamDate is undefined');
        }
      } else {
        console.error('Blood data is undefined');
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };
  
  useEffect(() => {
    fetchData();
  }, []); 

  const calculateHemoglobinPosition = (hemoglobin) => {
    if (hemoglobin <= 0) {
      return 0;
    } else if (hemoglobin > 0 && hemoglobin <= 12.0) {
      return ((hemoglobin - 0) / (12.0 - 0)) * 50; 
    } else if (hemoglobin > 12.0 && hemoglobin <= 24.0) {
      return 50 + ((hemoglobin - 12.0) / (24.0 - 12.0)) * 50; 
    } else {
      return 100; 
    }
  };
  const calculatefastingBloodSugarPosition = (fastingBloodSugar) => {
    if (fastingBloodSugar <= 80) {
      return 0; 
    } else if (fastingBloodSugar > 80 && fastingBloodSugar <= 100) {
      return ((fastingBloodSugar - 80) / (100 - 80)) * 33.3;
    } else if (fastingBloodSugar > 100 && fastingBloodSugar < 120) {
      return 33.3 + ((fastingBloodSugar - 100) / (120 - 100)) * 33.3;
    } else if (fastingBloodSugar >= 120 && fastingBloodSugar < 140) {
      return 66.6 + ((fastingBloodSugar - 120) / (140 - 120)) * 33.3;
    } else {
      return 100; 
    }
  };
  const calculateTotalCholesterolPosition = (totalCholesterol) => {
    if (totalCholesterol <= 160) {
      return 0; 
    } else if (totalCholesterol > 160 && totalCholesterol <= 200) {
      return ((totalCholesterol - 160) / (200 - 160)) * 33.3;
    } else if (totalCholesterol > 200 && totalCholesterol < 120) {
      return 33.3 + ((totalCholesterol - 200) / (240 - 200)) * 33.3;
    } else if (totalCholesterol >= 240 && totalCholesterol < 140) {
      return 66.6 + ((totalCholesterol - 240) / (280 - 240)) * 33.3;
    } else {
      return 100; 
    }
  };
  const calculateHdlcholesterolPosition = (hdlcholesterol) => {
    if (hdlcholesterol <= 0) {
      return 0;
    } else if (hdlcholesterol > 0 && hdlcholesterol <= 60.0) {
      return ((hdlcholesterol - 0) / (60.0 - 0)) * 50; 
    } else if (hdlcholesterol > 60.0 && hdlcholesterol <= 100.0) {
      return 50 + ((hdlcholesterol - 60.0) / (100.0 - 60.0)) * 50; 
    } else {
      return 100; 
    }
  };
  const calculateTriglyceridePosition = (triglyceride) => {
    if (triglyceride <= 0) {
      return 0;
    } else if (triglyceride > 0 && triglyceride <= 150.0) {
      return ((triglyceride - 0) / (150.0 - 0)) * 50; 
    } else if (triglyceride > 150.0 && triglyceride <= 300.0) {
      return 50 + ((triglyceride - 150.0) / (300.0 - 150.0)) * 50; 
    } else {
      return 100; 
    }
  };
  const calculateLdlcholesterolPosition = (ldlcholesterol) => {
    if (ldlcholesterol <= 0) {
      return 0;
    } else if (ldlcholesterol > 0 && ldlcholesterol <= 130.0) {
      return ((ldlcholesterol - 0) / (130.0 - 0)) * 50; 
    } else if (ldlcholesterol > 130.0 && ldlcholesterol <= 250.0) {
      return 50 + ((ldlcholesterol - 130.0) / (250.0 - 130.0)) * 50; 
    } else {
      return 100; 
    }
  };
  const calculateSerumCreatininePosition = (serumCreatinine) => {
    if (serumCreatinine <= 0) {
      return 0;
    } else if (serumCreatinine > 0 && serumCreatinine <= 1.5) {
      return ((serumCreatinine - 0) / (1.5 - 0)) * 50; 
    } else if (serumCreatinine > 1.5 && serumCreatinine <= 3.0) {
      return 50 + ((serumCreatinine - 1.5) / (3.0 - 1.5)) * 50; 
    } else {
      return 100; 
    }
  };
  const calculateGfrPosition = (gfr) => {
    if (gfr <= 0) {
      return 0;
    } else if (gfr > 0 && gfr <= 60.0) {
      return ((gfr - 0) / (60.0 - 0)) * 50; 
    } else if (gfr > 60.0 && gfr <= 120.0) {
      return 50 + ((gfr - 60.0) / (120.0 - 60.0)) * 50; 
    } else {
      return 100; 
    }
  };
  const calculateAstPosition = (ast) => {
    if (ast <= 0) {
      return 0;
    } else if (ast > 0 && ast <= 40.0) {
      return ((ast - 0) / (40.0 - 0)) * 50; 
    } else if (ast > 40.0 && ast <= 80.0) {
      return 50 + ((ast - 40.0) / (80.0 - 40.0)) * 50; 
    } else {
      return 100; 
    }
  };
  const calculateAltPosition = (alt) => {
    if (alt <= 0) {
      return 0;
    } else if (alt > 0 && alt <= 35.0) {
      return ((alt - 0) / (35.0 - 0)) * 50; 
    } else if (alt > 35.0 && alt <= 70.0) {
      return 50 + ((alt - 35.0) / (70.0 - 35.0)) * 50; 
    } else {
      return 100; 
    }
  };
  const calculateGptPosition = (gpt) => {
    if (gpt <= 0) {
      return 0;
    } else if (gpt > 0 && gpt <= 35.0) {
      return ((gpt - 0) / (35.0 - 0)) * 50; 
    } else if (gpt > 35.0 && gpt <= 70.0) {
      return 50 + ((gpt - 35.0) / (70.0 - 35.0)) * 50; 
    } else {
      return 100; 
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
        <Text style={styles.subTitleText}>혈액 검사</Text>
            <ScrollView style={[{paddingLeft: 20, paddingRight: 20}]}>
            <View style={styles.scrollView}>
              <Text>혈색소 (g/dL)</Text>
              <Text style={[styles.valueBox, {width: "10%"}, {left: `${calculateHemoglobinPosition(item.hemoglobin)-4}%`}]}>{item.hemoglobin}</Text>
              <Text style={{ left: `${calculateHemoglobinPosition(item.hemoglobin)}%` }}>|</Text>
              <View style={[{flexDirection: "row"}]}>
                {/* 빈혈 의심 구간 */}
                <View style={styles.leftBar}>
                  <Text>빈혈 의심</Text>
                </View>
                {/* 정상 구간 */}
                <View style={styles.rightBar}>
                  <Text>정상</Text>
                </View>
              </View>
              <Text style={styles.midValueText}>12.0</Text>
            </View>
            <View style={styles.scrollView}>
              <Text>공복 혈당 (mg/dL)</Text>
              <Text style={[styles.valueBox, {width: "10%"}, {left: `${calculatefastingBloodSugarPosition(item.fastingBloodSugar)-4}%`}]}>{item.fastingBloodSugar}</Text>
              <Text style={{ left: `${calculatefastingBloodSugarPosition(item.fastingBloodSugar)}%` }}>|</Text>
            <View style={[{flexDirection: "row"}]}>
              {/* 정상 구간 */}
              <View style={styles.bar1}>
                <Text>정상</Text>
              </View>
              {/* 공복 혈당 장애 의심 구간 */}
              <View style={styles.bar2}>
                <Text>공복 혈당 장애 의심</Text>
              </View>
              {/* 당뇨병 의심 구간 */}
              <View style={styles.bar3}>
                <Text>당뇨병 의심</Text>
              </View>
            </View>
            <View style={[{flexDirection: "row"}]}>
                <Text style={styles.firstValueText}>100.0</Text>
                <Text style={styles.finValueText}>120.0</Text>
              </View>
            </View>
            <View style={styles.scrollView}>
              <Text>총 콜레스테롤 (mg/dL)</Text>
              <Text style={[styles.valueBox, {width: "12%"}, {left: `${calculateTotalCholesterolPosition(item.totalCholesterol)-6}%`}]}>{item.totalCholesterol}</Text>
              <Text style={{ left: `${calculateTotalCholesterolPosition(item.totalCholesterol)}%` }}>|</Text>
            <View style={[{flexDirection: "row"}]}>
              {/* 정상 구간 */}
              <View style={styles.bar1}>
                <Text>정상</Text>
              </View>
              {/* 경계 구간 */}
              <View style={styles.bar2}>
                <Text>경계 의심</Text>
              </View>
              {/* 질환 의심 구간 */}
              <View style={styles.bar3}>
                <Text>질환 의심</Text>
              </View>
            </View>
            <View style={[{flexDirection: "row"}]}>
                <Text style={styles.firstValueText}>200.0</Text>
                <Text style={styles.finValueText}>240.0</Text>
              </View>
            </View>
            <View style={styles.scrollView}>
              <Text>고밀도 콜레스테롤 (mg/dL)</Text>
              <Text style={[styles.valueBox, {width: "10%"}, {left: `${calculateHdlcholesterolPosition(item.hdlcholesterol)-4}%`}]}>{item.hdlcholesterol}</Text>
              <Text style={{ left: `${calculateHdlcholesterolPosition(item.hdlcholesterol)}%` }}>|</Text>
              <View style={[{flexDirection: "row"}]}>
                {/* 정상 구간 */}
                <View style={styles.leftBar}>
                  <Text>정상</Text>
                </View>
                {/* 질환 의심 구간 */}
                <View style={styles.rightBar}>
                  <Text>질환 의심</Text>
                </View>
              </View>
              <Text style={styles.midValueText}>60.0</Text>
            </View>
            <View style={styles.scrollView}>
              <Text>중성 지방 (mg/dL)</Text>
              <Text style={[styles.valueBox, {width: "13%"}, {left: `${calculateTriglyceridePosition(item.triglyceride)-6}%`}]}>{item.triglyceride}</Text>
              <Text style={{ left: `${calculateTriglyceridePosition(item.triglyceride)}%` }}>|</Text>
              <View style={[{flexDirection: "row"}]}>
                {/* 정상 구간 */}
                <View style={styles.leftBar}>
                  <Text>정상</Text>
                </View>
                {/* 질환 의심 구간 */}
                <View style={styles.rightBar}>
                  <Text>질환 의심</Text>
                </View>
              </View>
              <Text style={styles.midValueText}>150.0</Text>
            </View>
            <View style={styles.scrollView}>
              <Text>저밀도 콜레스테롤 (mg/dL)</Text>
              <Text style={[styles.valueBox, {width: "13%"}, {left: `${calculateLdlcholesterolPosition(item.ldlcholesterol)-6}%`}]}>{item.ldlcholesterol}</Text>
              <Text style={{ left: `${calculateLdlcholesterolPosition(item.ldlcholesterol)}%` }}>|</Text>
              <View style={[{flexDirection: "row"}]}>
                {/* 정상 구간 */}
                <View style={styles.leftBar}>
                  <Text>정상</Text>
                </View>
                {/* 질환 의심 구간 */}
                <View style={styles.rightBar}>
                  <Text>질환 의심</Text>
                </View>
              </View>
              <Text style={styles.midValueText}>130.0</Text>
            </View>
            <View style={styles.scrollView}>
              <Text>혈청 크레아티닌 (mg/dL)</Text>
              <Text style={[styles.valueBox, {width: "10%"}, {left: `${calculateSerumCreatininePosition(item.serumCreatinine)-4}%`}]}>{item.serumCreatinine}</Text>
              <Text style={{ left: `${calculateSerumCreatininePosition(item.serumCreatinine)}%` }}>|</Text>
              <View style={[{flexDirection: "row"}]}>
                {/* 정상 구간 */}
                <View style={styles.leftBar}>
                  <Text>정상</Text>
                </View>
                {/* 신장 기능 이상 의심 구간 */}
                <View style={styles.rightBar}>
                  <Text>신장 기능 이상 의심</Text>
                </View>
              </View>
              <Text style={styles.midValueText}>1.5</Text>
            </View>
            <View style={styles.scrollView}>
              <Text>신 사구체 여과율 (e-GFR)</Text>
              <Text style={[styles.valueBox, {width: "10%"}, {left: `${calculateGfrPosition(item.gfr)-4}%`}]}>{item.gfr}</Text>
              <Text style={{ left: `${calculateGfrPosition(item.gfr)}%` }}>|</Text>
              <View style={[{flexDirection: "row"}]}>
                {/* 정상 구간 */}
                <View style={styles.leftBar}>
                  <Text>정상</Text>
                </View>
                {/* 신장 기능 이상 의심 구간 */}
                <View style={styles.rightBar}>
                  <Text>신장 기능 이상 의심</Text>
                </View>
              </View>
              <Text style={styles.midValueText}>60.0</Text>
            </View>
            <View style={styles.scrollView}>
              <Text>AST(SGOT) (IU/L)</Text>
              <Text style={[styles.valueBox, {width: "10%"}, {left: `${calculateAstPosition(item.ast)-4}%`}]}>{item.ast}</Text>
              <Text style={{ left: `${calculateAstPosition(item.ast)}%` }}>|</Text>
              <View style={[{flexDirection: "row"}]}>
                {/* 정상 구간 */}
                <View style={styles.leftBar}>
                  <Text>정상</Text>
                </View>
                {/* 간 기능 이상 의심 구간 */}
                <View style={styles.rightBar}>
                  <Text>간 기능 이상 의심</Text>
                </View>
              </View>
              <Text style={styles.midValueText}>40.0</Text>
            </View>
            <View style={styles.scrollView}>
              <Text>ALT(SGPT) (IU/L)</Text>
              <Text style={[styles.valueBox, {width: "10%"}, {left: `${calculateAltPosition(item.alt)-4}%`}]}>{item.alt}</Text>
              <Text style={{ left: `${calculateAltPosition(item.alt)}%` }}>|</Text>
              <View style={[{flexDirection: "row"}]}>
                {/* 정상 구간 */}
                <View style={styles.leftBar}>
                  <Text>정상</Text>
                </View>
                {/* 간 기능 이상 의심 구간 */}
                <View style={styles.rightBar}>
                  <Text>간 기능 이상 의심</Text>
                </View>
              </View>
              <Text style={styles.midValueText}>35.0</Text>
            </View>
            <View style={styles.scrollView}>
              <Text>감마 지티피(γ-GTP) (IU/L)</Text>
              <Text style={[styles.valueBox, {width: "10%"}, {left: `${calculateGptPosition(item.gpt)-4}%`}]}>{item.gpt}</Text>
              <Text style={{ left: `${calculateGptPosition(item.gpt)}%` }}>|</Text>
              <View style={[{flexDirection: "row"}]}>
                {/* 정상 구간 */}
                <View style={styles.leftBar}>
                  <Text>정상</Text>
                </View>
                {/* 간 기능 이상 의심 구간 */}
                <View style={styles.rightBar}>
                  <Text>간 기능 이상 의심</Text>
                </View>
              </View>
              <Text style={styles.midValueText}>35.0</Text>
            </View>
        <View style={styles.row}>
          <TouchableOpacity 
            style={styles.buttonBack}
            onPress={() => handleBlockPress(2, data)}
            activeOpacity={0.7}
          >
            <Text style={styles.buttonText}>뒤로 가기</Text>
          </TouchableOpacity>
          <TouchableOpacity 
            style={styles.button} 
            onPress={() => handleBlockPress(4, data)}
            activeOpacity={0.7}
          >
            <Text style={styles.buttonText}>나머지 검사 결과 보기</Text>
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
  leftBar: {
    alignItems: 'center',
    justifyContent: "center",
    width: '50%',
    height: 30,
    backgroundColor: 'rgba(118, 185, 71, 0.5)',
    borderTopLeftRadius: 6,
    borderBottomLeftRadius: 6,
  },
  rightBar: {
    alignItems: 'center',
    justifyContent: "center",
    width: '50%',
    height: 30,
    backgroundColor: 'rgba(82, 82, 82, 0.5)',
    borderTopRightRadius: 6,
    borderBottomRightRadius: 6, 
  },
  midValueText: {
    left: '47%',
    marginTop: 5,
    marginBottom: 20,
  },
  bar1: {
    alignItems: 'center',
    justifyContent: "center", 
    width: '33.3%',
    height: 30,
    backgroundColor: 'rgba(155, 211, 148, 0.5)',
    borderTopLeftRadius: 6,
    borderBottomLeftRadius: 6,
  },
  bar2: {
    alignItems: 'center',
    justifyContent: "center",
    width: '33.3%',
    height: 30,
    backgroundColor: 'rgba(71, 116, 58, 0.5)',
  },
  bar3: {
    alignItems: 'center',
    justifyContent: "center",
    width: '33.3%',
    height: 30,
    backgroundColor: 'rgba(82, 82, 82, 0.5)',
    borderTopRightRadius: 6,
    borderBottomRightRadius: 6,
  },
  firstValueText: {
    left: '240%',
    marginTop: 5,
    marginBottom: 20,
  },
  finValueText: {
    left: '420%',
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
