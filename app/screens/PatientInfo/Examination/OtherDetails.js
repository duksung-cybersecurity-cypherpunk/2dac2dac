import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
} from "react-native";
import { useNavigation } from "@react-navigation/native";

export default function OtherDetails({ route }) {
  const { userId, data } = route.params;
  console.log("userId", userId, data);
  const navigation = useNavigation();
  const [item, setItem] = useState([]);
  const [date, setDate] = useState('');

  // const handleBlockPress = (id, data) => {
  //   navigation.navigate("ExaminationInfoStack", { id, data }); 
  // };
  const handleBlockPress = () => {
    navigation.navigate("Examination", { userId });
  };

  const handleLoad = (userId, data) => {
    navigation.navigate("BloodDetails", { userId, data });
  };

  const fetchData = async () => {
    try {
      const response = await fetch(`http://203.252.213.209:8080/api/v1/healthList/healthScreening/${userId}/${data.hsId}`); 
      const Measurement = await response.json();
      if (Measurement.data) { 
        setItem(Measurement.data.otherTestInfo);
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

  const calculateDepressionPosition = (depression) => {
    if (depression <= 0) {
      return 0; 
    } else if (depression > 0 && depression <= 5) {
      return ((depression - 0) / (5 - 0)) * 25; // 10~5 범위를 0%~25%로 매핑
    } else if (depression > 5 && depression <= 10) {
      return 25 + ((depression - 5) / (10 - 5)) * 25; // 5~10 범위를 25%~50%로 매핑
    } else if (depression > 10 && depression <= 20) {
      return 50 + ((depression - 10) / (20 - 10)) * 25; // 10~20 범위를 50%~75%로 매핑
    } else if (depression > 20 && depression < 27) {
      return 75 + ((depression - 20) / (27 - 20)) * 25; // 20~27 범위를 75%~100%로 매핑
    } else {
      return 100;
    }
  };
  const calculateCognitiveDysfunctionPosition = (cognitiveDysfunction) => {
    if (cognitiveDysfunction <= 0) {
      return 0;
    } else if (cognitiveDysfunction > 0 && cognitiveDysfunction <= 6) {
      return ((cognitiveDysfunction - 0) / (6 - 0)) * 50; 
    } else if (cognitiveDysfunction > 6 && cognitiveDysfunction <= 12) {
      return 50 + ((cognitiveDysfunction - 6) / (12 - 6)) * 50; 
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
        <ScrollView style={[{paddingLeft: 20, paddingRight: 20}]}>
          <Text style={styles.subTitleText}>요 검사</Text>
          <Text>{item.urinaryProtein}</Text>
          
          <Text style={styles.subTitleText}>영상 검사</Text>
          <Text>{item.tbchestDisease}</Text>

          <Text style={styles.subTitleText}>문진</Text>
          {item.smoke && (
            <Text>금연 필요</Text>
          )}
          {item.drink && (
            <Text>절주 필요</Text>
          )}
          {!item.physicalActivity && (
            <Text>신체 활동 필요</Text>
          )}
          {!item.exercise && (
            <Text>근력 운동 필요</Text>
          )}

          <Text style={styles.subTitleText}>B형 간염</Text>
          {!item.isHepB ? (
            <Text>검사 미해당</Text>
          ) : (
            <>
              <View style={styles.row}>
                <Text>표면 항원</Text>
                <Text>{item.hepBSurfaceAntibody}</Text>
              </View>
              <View style={styles.row}>
                <Text>표면 항체</Text>
                <Text>{item.hepBSurfaceAntigen}</Text>
              </View>
              <View style={{ alignItems: 'flex-end' }}>
                <Text>{item.hepB}</Text>
              </View>
            </>
          )}

          <Text style={styles.subTitleText}>골밀도</Text>
          {!item.isBoneDensityTest ? (
            <Text>검사 미해당</Text>
          ) : (
            <>
              <Text>{item.boneDensityTest}</Text>
            </>
          )}
          
          <Text style={styles.subTitleText}>우울증</Text>
          {!item.isDepression ? (
            <Text>검사 미해당</Text>
          ) : (
            <>
              <View style={styles.scrollView}>
              <Text style={[styles.valueBox, {width: "25%"}, {left: 50}]}>{item.depression}</Text>
              <Text style={{ left: `${calculateDepressionPosition(item.depression)}%` }}>|</Text>
                <View style={[{flexDirection: "row"}]}>
                  <View style={styles.bar1}>
                    <Text>우울 증상 없음</Text>
                  </View>
                  <View style={[styles.bar2, {backgroundColor: 'rgba(118, 185, 71, 0.5)'}]}>
                    <Text>가벼운 우울</Text>
                  </View>
                  <View style={[styles.bar2, {backgroundColor: 'rgba(71, 116, 58, 0.5)'}]}>
                    <Text>중간 우울</Text>
                  </View>
                  <View style={styles.bar3}>
                    <Text>심한 우울 </Text>
                  </View>
                </View>
                <View style={[{flexDirection: "row"}]}>
                  <Text style={styles.zeroValueText}>0</Text>
                  <Text style={styles.firstValueText}>5</Text>
                  <Text style={styles.midValueText}>10</Text>
                  <Text style={styles.finValueText}>20</Text>
                  <Text style={styles.endValueText}>27</Text>
                </View>
              </View>
            </>
          )}

          <Text style={styles.subTitleText}>인지 기능 장애</Text>
          {!item.isCognitiveDysfunction ? (
            <Text>검사 미해당</Text>
          ) : (
            <>
              <View style={styles.scrollView}>
              <Text style={[styles.valueBox, {width: "30%"}, {left: `${calculateCognitiveDysfunctionPosition(item.cognitiveDysfunction)-40}%`}]}>{item.cognitiveDysfunction}</Text>
              <Text style={{ left: `${calculateCognitiveDysfunctionPosition(item.cognitiveDysfunction)-25}%` }}>|</Text>
                <View style={[{flexDirection: "row"}]}>
                  {/* 정상 구간 */}
                  <View style={styles.leftBar}>
                    <Text>특이 소견 없음</Text>
                  </View>
                  {/* 인지 기능 저하 의심 구간 */}
                  <View style={styles.rightBar}>
                    <Text>인지 기능 저하 의심</Text>
                  </View>
                </View>
                <Text style={styles.midValueText}>6</Text>
              </View>
            </>
          )}

          <Text style={styles.subTitleText}>노인 신체 기능 검사</Text>
          {!item.isElderlyPhysicalFunctionTest ? (
            <Text>검사 미해당</Text>
          ) : (
            <Text>{item.elderlyPhysicalFunctionTest}</Text>
          )}
          
          <Text style={styles.subTitleText}>노인 기능 평가</Text>
          {!item.isElderlyFunctionalAssessment ? (
            <Text>검사 미해당</Text>
          ) : (
            <>
            <View style={styles.row}>
              <Text>낙상</Text>
              <Text>{item.elderlyFunctionalAssessmentFalls}</Text>
            </View>
            <View style={styles.row}>
              <Text>일상생활 수행능력</Text>
              <Text>{item.elderlyFunctionalAssessmentADL}</Text>
            </View>
            <View style={styles.row}>
              <Text>일상생활 배뇨장애</Text>
              <Text>{item.elderlyFunctionalAssessmentUrinaryIncontinence}</Text>
            </View>
            <View style={styles.row}>
              <Text>예방접종</Text>
              <Text>{item.elderlyFunctionalAssessmentVaccination}</Text>
            </View>
          </>
          )}

        <View style={styles.row}>
            <TouchableOpacity 
                style={styles.buttonBack}
                onPress={() => handleLoad(userId, data)}
                activeOpacity={0.7}
            >
                <Text style={styles.buttonText}>뒤로 가기</Text>
            </TouchableOpacity>
            <TouchableOpacity 
                style={styles.button} 
                onPress={() => handleBlockPress(userId)}
                activeOpacity={0.7}
            >
                <Text style={styles.buttonText}>처음으로 돌아가기</Text>
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
    paddingTop: 20,
    paddingLeft: 20,
  },
  subTitleText: {
    fontSize: 18,
    fontWeight: "bold",
    paddingTop: 20,
    paddingBottom: 10,
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
    width: '25%',
    height: 30,
    backgroundColor: 'rgba(155, 211, 148, 0.5)',
    borderTopLeftRadius: 6,
    borderBottomLeftRadius: 6, 
  },
  bar2: {
    alignItems: 'center',
    justifyContent: "center",
    width: '25%',
    height: 30,
  },
  bar3: {
    alignItems: 'center',
    justifyContent: "center",
    width: '25%',
    height: 30,
    backgroundColor: 'rgba(82, 82, 82, 0.5)',
    borderTopRightRadius: 6,
    borderBottomRightRadius: 6,
  },
  zeroValueText: {
    left: '0%',
    marginTop: 5,
    marginBottom: 20,
  },
  firstValueText: {
    left: '180%',
    marginTop: 5,
    marginBottom: 20,
  },
  midValueText: {
    left: '360%',
    marginTop: 5,
    marginBottom: 20,
  },
  finValueText: {
    left: '530%',
    marginTop: 5,
    marginBottom: 20,
  },
  endValueText: {
    left: '690%',
    marginTop: 5,
    marginBottom: 20,
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
