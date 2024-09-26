import React, { useEffect, useState } from "react";
import {
  View,
  Text,
  TouchableOpacity,
  ScrollView,
  FlatList,
  StyleSheet,
} from "react-native";
import { useNavigation } from "@react-navigation/native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";

export const getCurrentWeekDays = () => {
  const today = new Date();
  const weekDays = [];
  const daysOfWeek = [
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
  ];

  for (let i = 0; i < 7; i++) {
    const date = new Date(today);
    date.setDate(today.getDate() + (i - today.getDay()));
    weekDays.push({
      name: daysOfWeek[i],
      date: date.toISOString().split("T")[0],
      isToday: i === today.getDay(),
    });
  }
  return weekDays;
};
export const ReservationDate = (dateName) => {
  const today = new Date();
  const currentDayIndex = today.getDay();
  const daysOfWeek = [
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
  ];

  const selectedDayIndex = daysOfWeek.indexOf(dateName);
  const daysUntilSelected = (selectedDayIndex - currentDayIndex + 7) % 7;
  const selectedDate = new Date(today);
  selectedDate.setDate(today.getDate() + daysUntilSelected);
  return selectedDate.toISOString().split("T")[0];
};

export const formatDate = (dateName) => {
  const date = new Date(dateName);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0"); // Months are zero-based
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
};
