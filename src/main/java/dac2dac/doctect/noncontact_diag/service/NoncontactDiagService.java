package dac2dac.doctect.noncontact_diag.service;

import static dac2dac.doctect.common.utils.DiagTimeUtils.findTodayCloseTime;
import static dac2dac.doctect.common.utils.DiagTimeUtils.findTodayOpenTime;
import static dac2dac.doctect.common.utils.DiagTimeUtils.isAgencyOpenNow;

import dac2dac.doctect.doctor.repository.DepartmentRepository;
import dac2dac.doctect.doctor.repository.DepartmentTagRepository;
import dac2dac.doctect.doctor.repository.DoctorRepository;
import dac2dac.doctect.noncontact_diag.dto.response.DepartmentInfo;
import dac2dac.doctect.noncontact_diag.dto.response.DepartmentListDto;
import dac2dac.doctect.noncontact_diag.dto.response.DoctorItem;
import dac2dac.doctect.noncontact_diag.dto.response.DoctorListDto;
import dac2dac.doctect.review.repository.ReviewRepository;
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
                    .id(doctor.getId())
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
}
