import React, { useState, useEffect, useCallback } from "react";
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Image,
  TextInput
} from "react-native";
import axios from "axios";
import dayjs from "dayjs";
import { useNavigation, useFocusEffect } from "@react-navigation/native";
import AsyncStorage from "@react-native-async-storage/async-storage";

export default function PrescriptionWriting({ route }) {
    const navigation = useNavigation();
    const { doctorId, reservationId } = route.params;
    
    return (
      <View style={styles.screenContainer}>
        <Text style={[styles.titleText, { paddingTop: 30 }]}>처방전 작성 예정</Text>
      </View>
        //     <View style={styles.paymentContent}>
        //         <Text style={styles.paymentText}>진료비 금액 입력</Text>
        //         <TextInput
        //             style={styles.input}
        //             placeholder="금액을 입력하세요"
        //             keyboardType="numeric"
        //             value={price}
        //             onChangeText={setPrice}
        //         />
        //         <View style={styles.buttonContainer}>
        //             <TouchableOpacity
        //                 style={[styles.button, styles.submitButton]}
        //                 onPress={onSubmit}
        //             >
        //                 <Text style={styles.buttonText}>청구하기</Text>
        //             </TouchableOpacity>
        //             <TouchableOpacity
        //                 style={[styles.button, styles.cancelButton]}
        //                 onPress={onClose}
        //             >
        //                 <Text style={styles.buttonText}>취소</Text>
        //             </TouchableOpacity>
        //         </View>
        //     </View>
    );
}

const styles = StyleSheet.create({
  screenContainer: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "white",
  },
  paymentContent: {
    //width: 350,
    padding: 30,
    backgroundColor: "white",
    borderRadius: 10,
  },
  paymentText: {
    marginBottom: 10,
  },
  input: {
    height: 50,
    borderColor: "gray",
    borderWidth: 1,
    marginBottom: 15,
    paddingLeft: 10,
  },
  buttonContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  button: {
    flex: 1,
    marginHorizontal: 3,
    borderRadius: 6,
  },
  submitButton: {
    backgroundColor: "#9BD394",
    alignItems: "center",
    padding: 10,
  },
  cancelButton: {
    borderWidth: 1,
    borderColor: "#9BD394",
    alignItems: "center",
    padding: 10,
  },
  buttonText: {
    color: "black",
  },
});
