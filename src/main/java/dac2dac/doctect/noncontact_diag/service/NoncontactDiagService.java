package dac2dac.doctect.noncontact_diag.service;

import static dac2dac.doctect.common.utils.DiagTimeUtils.findTodayCloseTime;
import static dac2dac.doctect.common.utils.DiagTimeUtils.findTodayOpenTime;
import static dac2dac.doctect.common.utils.DiagTimeUtils.isAgencyOpenNow;

import dac2dac.doctect.agency.entity.Hospital;
import dac2dac.doctect.bootpay.entity.PaymentMethod;
import dac2dac.doctect.bootpay.entity.constant.ActiveStatus;
import dac2dac.doctect.bootpay.repository.PaymentMethodRepository;
import dac2dac.doctect.common.constant.ErrorCode;
import dac2dac.doctect.common.entity.DiagTime;
import dac2dac.doctect.common.error.exception.BadRequestException;
import dac2dac.doctect.common.error.exception.NotFoundException;
import dac2dac.doctect.common.error.exception.UnauthorizedException;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.doctor.repository.DepartmentRepository;
import dac2dac.doctect.doctor.repository.DepartmentTagRepository;
import dac2dac.doctect.doctor.repository.DoctorRepository;
import dac2dac.doctect.noncontact_diag.dto.request.NoncontactDiagAppointmentRequestDto;
import dac2dac.doctect.noncontact_diag.dto.response.DepartmentInfo;
import dac2dac.doctect.noncontact_diag.dto.response.DepartmentListDto;
import dac2dac.doctect.noncontact_diag.dto.response.DoctorDto;
import dac2dac.doctect.noncontact_diag.dto.response.DoctorInfo;
import dac2dac.doctect.noncontact_diag.dto.response.DoctorIntroduction;
import dac2dac.doctect.noncontact_diag.dto.response.DoctorItem;
import dac2dac.doctect.noncontact_diag.dto.response.DoctorListDto;
import dac2dac.doctect.noncontact_diag.dto.response.NoncontactDiagFormDoctorInfo;
import dac2dac.doctect.noncontact_diag.dto.response.NoncontactDiagFormDto;
import dac2dac.doctect.noncontact_diag.dto.response.NoncontactDiagFormInfo;
import dac2dac.doctect.noncontact_diag.dto.response.NoncontactDiagShortFormDto;
import dac2dac.doctect.noncontact_diag.entity.NoncontactDiagReservation;
import dac2dac.doctect.noncontact_diag.entity.Symptom;
import dac2dac.doctect.noncontact_diag.entity.constant.ReservationStatus;
import dac2dac.doctect.noncontact_diag.repository.NoncontactDiagReservationRepository;
import dac2dac.doctect.noncontact_diag.repository.SymptomRepository;
import dac2dac.doctect.user.entity.User;
import dac2dac.doctect.user.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private final UserRepository userRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final SymptomRepository symptomRepository;
    private final NoncontactDiagReservationRepository noncontactDiagReservationRepository;

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
            .map(doctor -> DoctorItem.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .hospitalName(doctor.getHospital().getName())
                .profilePath(doctor.getProfileImagePath())
                .isOpen(isAgencyOpenNow(doctor.getHospital().getDiagTime()))
                .todayOpenTime(findTodayOpenTime(doctor.getHospital().getDiagTime()))
                .todayCloseTime(findTodayCloseTime(doctor.getHospital().getDiagTime()))
                .build())
            .collect(Collectors.toList());

        return DoctorListDto.builder()
            .cnt(doctorItemList.size())
            .doctorItemList(doctorItemList)
            .build();
    }

    public DoctorDto getDoctorDetailInfo(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.DOCTOR_NOT_FOUND));

        Hospital doctorHospital = doctor.getHospital();

        DoctorInfo doctorInfo = DoctorInfo.builder()
            .name(doctor.getName())
            .hospitalName(doctorHospital.getName())
            .profilePath(doctor.getProfileImagePath())
            .isOpen(isAgencyOpenNow(doctorHospital.getDiagTime()))
            .todayOpenTime(findTodayOpenTime(doctorHospital.getDiagTime()))
            .todayCloseTime(findTodayCloseTime(doctorHospital.getDiagTime()))
            .build();

        DoctorIntroduction doctorIntroduction = DoctorIntroduction.builder()
            .department(doctor.getDepartment().getDepartmentName())
            .diagTime(doctorHospital.getDiagTime())
            .oneLiner(doctor.getOneLiner())
            .experience(doctor.getExperience())
            .hospitalId(doctorHospital.getId())
            .hospitalName(doctorHospital.getName())
            .hospitalAddress(doctorHospital.getAddress())
            .hospitalLongitude(doctorHospital.getLongitude())
            .hospitalLatitude(doctorHospital.getLatitude())
            .hospitalThumnail(doctorHospital.getThumnail())
            .build();

        return DoctorDto.builder()
            .doctorInfo(doctorInfo)
            .doctorIntroduction(doctorIntroduction)
            .build();
    }

    public void appointNoncontactDiag(Long userId, NoncontactDiagAppointmentRequestDto requestDto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Doctor doctor = doctorRepository.findById(requestDto.getDoctorId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.DOCTOR_NOT_FOUND));

        PaymentMethod paymentMethod = paymentMethodRepository.findPaymentMethodByBillingKey(requestDto.getBillingKey())
            .orElseThrow(() -> new NotFoundException(ErrorCode.PAYMENT_METHOD_NOT_FOUND));

        //* 결제 방식이 활성화되어 있는지 확인한다.
        if (paymentMethod.getStatus() == ActiveStatus.INACTIVE) {
            throw new UnauthorizedException(ErrorCode.PAYMENT_METHOD_NOT_FOUND);
        }

        int timeValue = Integer.parseInt(requestDto.getReservationTime());
        LocalTime reservationTime = LocalTime.of(timeValue / 100, timeValue % 100);

        //* 유효한 진료 예약 날짜 및 시간인지 검증
        isValidReservation(requestDto.getReservationDate(), reservationTime, doctor.getHospital().getDiagTime());

        //* 동의 여부 확인
        if (!requestDto.getIsConsent()) {
            throw new BadRequestException(ErrorCode.CONSENT_BAD_REQUEST);
        }

        Symptom symptom = Symptom.builder()
            .isPrescribedDrug(requestDto.getIsPrescribedDrug())
            .prescribedDrug(requestDto.getIsPrescribedDrug() ? requestDto.getPrescribedDrug() : "")
            .isAllergicSymptom(requestDto.getIsAllergicSymptom())
            .allergicSymptom(requestDto.getIsAllergicSymptom() ? requestDto.getAllergicSymptom() : "")
            .isInbornDisease(requestDto.getIsInbornDisease())
            .inbornDisease(requestDto.getIsInbornDisease() ? requestDto.getInbornDisease() : "")
            .additionalInformation(requestDto.getAdditionalInformation())
            .build();

        symptomRepository.save(symptom);

        NoncontactDiagReservation noncontactDiagReservation = NoncontactDiagReservation.builder()
            .reservationDate(requestDto.getReservationDate())
            .reservationTime(reservationTime)
            .diagType(requestDto.getDiagType())
            .isConsent(requestDto.getIsConsent())
            .status(ReservationStatus.SIGN_UP)
            .doctor(doctor)
            .paymentMethod(paymentMethod)
            .user(user)
            .symptom(symptom)
            .build();

        noncontactDiagReservationRepository.save(noncontactDiagReservation);
    }

    public void cancelNoncontactDiag(Long userId, Long diagId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        NoncontactDiagReservation findNoncontactDiagReservation = noncontactDiagReservationRepository.findById(diagId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_DIAGNOSIS_RESERVATION_NOT_FOUND));

        //* 유저와 조회한 진료 내역에 해당하는 유저가 다를 경우에 대한 예외처리를 수행한다.
        if (!user.getId().equals(findNoncontactDiagReservation.getUser().getId())) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
        }

        findNoncontactDiagReservation.cancelReservation();
        noncontactDiagReservationRepository.save(findNoncontactDiagReservation);
    }

    public NoncontactDiagFormDto getNoncontactDiagForm(Long userId, Long diagId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        NoncontactDiagReservation findNoncontactDiagReservation = noncontactDiagReservationRepository.findById(diagId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_DIAGNOSIS_RESERVATION_NOT_FOUND));

        //* 유저와 조회한 진료 내역에 해당하는 유저가 다를 경우에 대한 예외처리를 수행한다.
        if (!user.getId().equals(findNoncontactDiagReservation.getUser().getId())) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
        }

        //* 예약한 비대면 진료의 상태가 예약 취소인 경우에 대한 예외처리를 수행한다.
        if (findNoncontactDiagReservation.getStatus() == ReservationStatus.CANCEL) {
            throw new BadRequestException(ErrorCode.CANCELED_FORM_BAD_REQUEST);
        }

        Doctor findDoctor = findNoncontactDiagReservation.getDoctor();
        Hospital doctorHospital = findDoctor.getHospital();

        //* 의사 정보
        NoncontactDiagFormDoctorInfo noncontactDiagFormDoctorInfo = NoncontactDiagFormDoctorInfo.builder()
            .doctorId(findDoctor.getId())
            .doctorName(findDoctor.getName())
            .doctorHospitalName(findDoctor.getHospital().getName())
            .doctorIsOpenNow(isAgencyOpenNow(doctorHospital.getDiagTime()))
            .doctorTodayOpenTime(findTodayOpenTime(doctorHospital.getDiagTime()))
            .doctorTodayCloseTime(findTodayCloseTime(doctorHospital.getDiagTime()))
            .build();

        Symptom findSymptom = findNoncontactDiagReservation.getSymptom();

        //* 예약 신청 세부 정보
        NoncontactDiagFormInfo noncontactDiagFormInfo = NoncontactDiagFormInfo.builder()
            .signupDate(findNoncontactDiagReservation.getCreateDate())
            .reservationDate(LocalDateTime.of(findNoncontactDiagReservation.getReservationDate(), findNoncontactDiagReservation.getReservationTime()))
            .department(findDoctor.getDepartment().getDepartmentName())
            .diagType(findNoncontactDiagReservation.getDiagType().getNoncontactDiagTypeName())
            .isPrescribedDrug(findSymptom.getIsPrescribedDrug())
            .prescribedDrug(findSymptom.getPrescribedDrug())
            .isAllergicSymptom(findSymptom.getIsAllergicSymptom())
            .allergicSymptom(findSymptom.getAllergicSymptom())
            .isInbornDisease(findSymptom.getIsInbornDisease())
            .inbornDisease(findSymptom.getInbornDisease())
            .additionalInformation(findSymptom.getAdditionalInformation())
            .build();

        return NoncontactDiagFormDto.builder()
            .noncontactFormDoctorInfo(noncontactDiagFormDoctorInfo)
            .noncontactDiagFormInfo(noncontactDiagFormInfo)
            .build();
    }

    public NoncontactDiagShortFormDto getNoncontactDiagSimpleForm(Long userId, Long diagId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        NoncontactDiagReservation findNoncontactDiagReservation = noncontactDiagReservationRepository.findById(diagId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_DIAGNOSIS_RESERVATION_NOT_FOUND));

        //* 유저와 조회한 진료 내역에 해당하는 유저가 다를 경우에 대한 예외처리를 수행한다.
        if (!user.getId().equals(findNoncontactDiagReservation.getUser().getId())) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
        }

        //* 예약한 비대면 진료의 상태가 예약 취소인 경우에 대한 예외처리를 수행한다.
        if (findNoncontactDiagReservation.getStatus() == ReservationStatus.CANCEL) {
            throw new BadRequestException(ErrorCode.CANCELED_FORM_BAD_REQUEST);
        }

        return NoncontactDiagShortFormDto.builder()
            .signupDate(findNoncontactDiagReservation.getCreateDate())
            .reservationDate(LocalDateTime.of(findNoncontactDiagReservation.getReservationDate(), findNoncontactDiagReservation.getReservationTime()))
            .department(findNoncontactDiagReservation.getDoctor().getDepartment().getDepartmentName())
            .diagType(findNoncontactDiagReservation.getDiagType().getNoncontactDiagTypeName())
            .build();
    }

    public NoncontactDiagShortFormDto getApproachingNoncontactDiag(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // 현재 시간 이후의 COMPLETE 상태인 예약 중에서 가장 가까운 예약 찾기
        LocalDateTime now = LocalDateTime.now();
        NoncontactDiagReservation closestNoncontactDiagReservation = noncontactDiagReservationRepository.findByUserIdAndStatus(userId, ReservationStatus.COMPLETE).stream()
            .filter(reservation -> LocalDateTime.of(reservation.getReservationDate(), reservation.getReservationTime()).isAfter(now))
            .sorted(Comparator.comparing(reservation -> LocalDateTime.of(reservation.getReservationDate(), reservation.getReservationTime())))
            .findFirst()
            .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_DIAGNOSIS_RESERVATION_NOT_FOUND));

        //* 유저와 조회한 진료 내역에 해당하는 유저가 다를 경우에 대한 예외처리를 수행한다.
        if (!user.getId().equals(closestNoncontactDiagReservation.getUser().getId())) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
        }

        return NoncontactDiagShortFormDto.builder()
            .signupDate(closestNoncontactDiagReservation.getCreateDate())
            .reservationDate(LocalDateTime.of(closestNoncontactDiagReservation.getReservationDate(), closestNoncontactDiagReservation.getReservationTime()))
            .department(closestNoncontactDiagReservation.getDoctor().getDepartment().getDepartmentName())
            .diagType(closestNoncontactDiagReservation.getDiagType().getNoncontactDiagTypeName())
            .build();
    }

    public static void isValidReservation(LocalDate reservationDate, LocalTime reservationTime, DiagTime diagTime) {
        // 진료 예약 날짜 확인
        LocalDate today = LocalDate.now();
        if (reservationDate.isBefore(today) || reservationDate.isAfter(today.plusDays(6))) {
            new BadRequestException(ErrorCode.RESERVATION_DATE_BAD_REQUEST);
        }

        // 진료 예약 시간 확인
        Integer openTime = findTodayOpenTime(diagTime);
        Integer closeTime = findTodayCloseTime(diagTime);

        LocalTime reservationTimeInMinutes = LocalTime.of(reservationTime.getHour(), reservationTime.getMinute());
        LocalTime openTimeInMinutes = LocalTime.of(openTime / 100, openTime % 100);
        LocalTime closeTimeInMinutes = LocalTime.of(closeTime / 100, closeTime % 100);

        if (reservationTimeInMinutes.isBefore(openTimeInMinutes) || reservationTimeInMinutes.isAfter(closeTimeInMinutes)) {
            new BadRequestException(ErrorCode.RESERVATION_TIME_BAD_REQUEST);
        }
    }

}
