package dac2dac.doctect.common.utils;

import dac2dac.doctect.common.entity.DiagTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DiagTimeUtils {

    private DiagTimeUtils() {
        // 유틸리티 클래스 인스턴스화 방지
    }

    public static boolean isAgencyOpenNow(DiagTime diagTime) {
        Integer todayOpenTime = findTodayOpenTime(diagTime);
        Integer todayCloseTime = findTodayCloseTime(diagTime);

        if (todayOpenTime != null && todayCloseTime != null) {
            // 오늘의 클로즈 시간이 2400을 넘는 경우 새벽으로 변환
            if (todayCloseTime > 2400) {
                todayCloseTime = todayCloseTime - 2400;
            }

            LocalTime now = LocalTime.now();
            LocalTime startTime = LocalTime.parse(String.format("%04d", todayOpenTime), DateTimeFormatter.ofPattern("HHmm"));
            LocalTime endTime;

            if (todayCloseTime == 2400) {
                endTime = LocalTime.MAX;
            } else {
                endTime = LocalTime.parse(String.format("%04d", todayCloseTime), DateTimeFormatter.ofPattern("HHmm"));
            }
            // 현재 시간이 오픈 시간과 클로즈 시간 사이에 있는지 확인
            return !now.isBefore(startTime) && now.isBefore(endTime);
        } else {
            return false;
        }
    }

    public static Integer findTodayOpenTime(DiagTime diagTime) {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();

        switch (dayOfWeek) {
            case MONDAY:
                return diagTime.getDiagTimeMonOpen();
            case TUESDAY:
                return diagTime.getDiagTimeTuesOpen();
            case WEDNESDAY:
                return diagTime.getDiagTimeWedsOpen();
            case THURSDAY:
                return diagTime.getDiagTimeThursOpen();
            case FRIDAY:
                return diagTime.getDiagTimeFriOpen();
            case SATURDAY:
                return diagTime.getDiagTimeSatOpen();
            case SUNDAY:
                return diagTime.getDiagTimeSunOpen();
            default:
                return 0;
        }
    }

    public static Integer findOpenTime(DiagTime diagTime, LocalDate reservationDate) {
        DayOfWeek dayOfWeek = reservationDate.getDayOfWeek();

        switch (dayOfWeek) {
            case MONDAY:
                return diagTime.getDiagTimeMonOpen();
            case TUESDAY:
                return diagTime.getDiagTimeTuesOpen();
            case WEDNESDAY:
                return diagTime.getDiagTimeWedsOpen();
            case THURSDAY:
                return diagTime.getDiagTimeThursOpen();
            case FRIDAY:
                return diagTime.getDiagTimeFriOpen();
            case SATURDAY:
                return diagTime.getDiagTimeSatOpen();
            case SUNDAY:
                return diagTime.getDiagTimeSunOpen();
            default:
                return 0;
        }
    }

    public static Integer findTodayCloseTime(DiagTime diagTime) {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();

        switch (dayOfWeek) {
            case MONDAY:
                return diagTime.getDiagTimeMonClose();
            case TUESDAY:
                return diagTime.getDiagTimeTuesClose();
            case WEDNESDAY:
                return diagTime.getDiagTimeWedsClose();
            case THURSDAY:
                return diagTime.getDiagTimeThursClose();
            case FRIDAY:
                return diagTime.getDiagTimeFriClose();
            case SATURDAY:
                return diagTime.getDiagTimeSatClose();
            case SUNDAY:
                return diagTime.getDiagTimeSunClose();
            default:
                return 0;
        }
    }

    public static Integer findCloseTime(DiagTime diagTime, LocalDate reservationDate) {
        DayOfWeek dayOfWeek = reservationDate.getDayOfWeek();

        switch (dayOfWeek) {
            case MONDAY:
                return diagTime.getDiagTimeMonClose();
            case TUESDAY:
                return diagTime.getDiagTimeTuesClose();
            case WEDNESDAY:
                return diagTime.getDiagTimeWedsClose();
            case THURSDAY:
                return diagTime.getDiagTimeThursClose();
            case FRIDAY:
                return diagTime.getDiagTimeFriClose();
            case SATURDAY:
                return diagTime.getDiagTimeSatClose();
            case SUNDAY:
                return diagTime.getDiagTimeSunClose();
            default:
                return 0;
        }
    }
}
