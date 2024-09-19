import React from "react";
import { StyleSheet, Text, View } from "react-native";

export default function MedicalHistory() {
  return (
    <View style={styles.container}>
      <Text>MedicalHistory</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
});
