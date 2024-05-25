package dac2dac.doctect.noncontact_diag.service;

import dac2dac.doctect.common.entity.DiagTime;
import dac2dac.doctect.doctor.repository.DepartmentRepository;
import dac2dac.doctect.doctor.repository.DepartmentTagRepository;
import dac2dac.doctect.doctor.repository.DoctorRepository;
import dac2dac.doctect.noncontact_diag.dto.response.DepartmentInfo;
import dac2dac.doctect.noncontact_diag.dto.response.DepartmentListDto;
import dac2dac.doctect.noncontact_diag.dto.response.DoctorItem;
import dac2dac.doctect.noncontact_diag.dto.response.DoctorListDto;
import dac2dac.doctect.review.repository.ReviewRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoncontactDiagService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentTagRepository departmentTagRepository;
    private final DoctorRepository doctorRepository;
    private final ReviewRepository reviewRepository;

    public DepartmentListDto getDepartmentList() {
        List<DepartmentInfo> departmentInfoList = departmentRepository.findAll().stream()
            .map(department -> {
                List<String> tags = departmentTagRepository.findByDepartmentId(department.getId()).stream()
                    .map(departmentTag -> departmentTag.getTag())
                    .collect(Collectors.toList());

                return DepartmentInfo.builder()
                    .departmentId(department.getId())
                    .departmentName(department.getDepartmentName())
                    .tags(tags)
                    .build();
            })
            .sorted(Comparator.comparing(DepartmentInfo::getDepartmentName))
            .collect(Collectors.toList());

        return DepartmentListDto.builder()
            .cnt(departmentInfoList.size())
            .departmentInfoList(departmentInfoList)
            .build();
    }

    public DoctorListDto getDoctorList(Long departmentId) {
        List<DoctorItem> doctorItemList = doctorRepository.findByDepartmentId(departmentId).stream()
            .map(doctor -> {
                Integer reviewCnt = reviewRepository.findByDoctorId(doctor.getId()).size();

                return DoctorItem.builder()
                    .name(doctor.getName())
                    .hospitalName(doctor.getHospital().getName())
                    .profilePath(doctor.getProfileImagePath())
                    .averageRating(doctor.getAvarageRating())
                    .reviewCnt(reviewCnt)
                    .isOpen(isAgencyOpenNow(doctor.getDiagTime()))
                    .todayOpenTime(findTodayOpenTime(doctor.getDiagTime()))
                    .todayCloseTime(findTodayCloseTime(doctor.getDiagTime()))
                    .build();
            })
            .collect(Collectors.toList());

        return DoctorListDto.builder()
            .cnt(doctorItemList.size())
            .doctorItemList(doctorItemList)
            .build();
    }

    private boolean isAgencyOpenNow(DiagTime diagTime) {
        Integer todayOpenTime = findTodayOpenTime(diagTime);
        Integer todayCloseTime = findTodayCloseTime(diagTime);

        if (todayOpenTime != null && todayCloseTime != null) {
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


    private static Integer findTodayOpenTime(DiagTime diagTime) {
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
        }
        return 0;
    }

    private static Integer findTodayCloseTime(DiagTime diagTime) {
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
        }
        return 0;
    }
}
