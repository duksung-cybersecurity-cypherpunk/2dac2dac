package dac2dac.doctect.doctor.service;

import dac2dac.doctect.agency.entity.Hospital;
import dac2dac.doctect.agency.repository.HospitalRepository;
import dac2dac.doctect.common.constant.ErrorCode;
import dac2dac.doctect.common.error.exception.BadRequestException;
import dac2dac.doctect.common.error.exception.NotFoundException;
import dac2dac.doctect.doctor.dto.DoctorDTO;
import dac2dac.doctect.doctor.dto.request.RejectReservationRequest;
import dac2dac.doctect.doctor.dto.response.AcceptedReservationItemList;
import dac2dac.doctect.doctor.dto.response.PatientInfoDto;
import dac2dac.doctect.doctor.dto.response.RequestReservationFormDto;
import dac2dac.doctect.doctor.dto.response.RequestReservationItemList;
import dac2dac.doctect.doctor.dto.response.ReservationItem;
import dac2dac.doctect.doctor.dto.response.ReservationListDto;
import dac2dac.doctect.doctor.entity.Department;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.doctor.repository.DepartmentRepository;
import dac2dac.doctect.doctor.repository.DoctorRepository;
import dac2dac.doctect.noncontact_diag.dto.response.NoncontactDiagFormInfo;
import dac2dac.doctect.noncontact_diag.entity.NoncontactDiagReservation;
import dac2dac.doctect.noncontact_diag.entity.Symptom;
import dac2dac.doctect.noncontact_diag.entity.constant.ReservationStatus;
import dac2dac.doctect.noncontact_diag.repository.NoncontactDiagReservationRepository;
import dac2dac.doctect.user.entity.User;
import dac2dac.doctect.user.entity.constant.Gender;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import dac2dac.doctect.user.jwt.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DoctorService {

    private final NoncontactDiagReservationRepository noncontactDiagReservationRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    private JWTUtil jwtUtil;

    // Constructor that initializes all required fields
    @Autowired
    public DoctorService(DoctorRepository doctorRepository,
                         HospitalRepository hospitalRepository,
                         DepartmentRepository departmentRepository,
                         NoncontactDiagReservationRepository noncontactDiagReservationRepository,
                         JWTUtil jwtUtil) {
        this.doctorRepository = doctorRepository;
        this.hospitalRepository = hospitalRepository;
        this.departmentRepository = departmentRepository;
        this.noncontactDiagReservationRepository = noncontactDiagReservationRepository;
        this.jwtUtil = jwtUtil;  // Initialize JWTUtil as well
    }

    public ReservationListDto getReservations(Long doctorId, String reservationDate) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DOCTOR_NOT_FOUND));

        // 날짜에 해당하면서 doctor id에 해당하는 예약 조회
        LocalDate localReservationDate = LocalDate.parse(reservationDate);
        List<NoncontactDiagReservation> noncontactDiagReservationList = noncontactDiagReservationRepository.findByDoctorIdAndReservationDate(doctorId, localReservationDate)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_DIAGNOSIS_RESERVATION_NOT_FOUND));

        // 요청된 예약: SIGN_UP 상태인 예약 필터링
        List<NoncontactDiagReservation> requestReservations = noncontactDiagReservationList.stream()
                .filter(reservation -> ReservationStatus.SIGN_UP.equals(reservation.getStatus()))
                .collect(Collectors.toList());

        List<ReservationItem> requestReservationItems = requestReservations.stream()
                .map(r -> ReservationItem.builder()
                        .reservationId(r.getId())
                        .signupDate(r.getCreateDate())
                        .patientName(r.getUser().getUsername())
                        .reservationDate(LocalDateTime.of(r.getReservationDate(), r.getReservationTime()))
                        .build())
                .sorted(Comparator.comparing(ReservationItem::getSignupDate))
                .collect(Collectors.toList());

        RequestReservationItemList requestReservationItemList = RequestReservationItemList.builder()
                .totalCnt(requestReservationItems.size())
                .requestReservationItemList(requestReservationItems)
                .build();

        // 수락된 예약: COMPLETE 상태인 예약 필터링
        List<NoncontactDiagReservation> acceptedReservations = noncontactDiagReservationList.stream()
                .filter(reservation -> ReservationStatus.COMPLETE.equals(reservation.getStatus()))
                .collect(Collectors.toList());

        List<ReservationItem> acceptedReservationItems = acceptedReservations.stream()
                .map(r -> ReservationItem.builder()
                        .reservationId(r.getId())
                        .signupDate(r.getCreateDate())
                        .patientName(r.getUser().getUsername())
                        .reservationDate(LocalDateTime.of(r.getReservationDate(), r.getReservationTime()))
                        .build())
                .sorted(Comparator.comparing(ReservationItem::getReservationDate))
                .collect(Collectors.toList());

        AcceptedReservationItemList acceptedReservationItemList = AcceptedReservationItemList.builder()
                .totalCnt(acceptedReservationItems.size())
                .acceptedReservationItemList(acceptedReservationItems)
                .build();

        return ReservationListDto.builder()
                .requestReservationList(requestReservationItemList)
                .acceptedReservationList(acceptedReservationItemList)
                .build();
    }

    public RequestReservationFormDto getReservationForm(Long doctorId, Long reservationId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DOCTOR_NOT_FOUND));

        NoncontactDiagReservation findNoncontactDiagReservation = noncontactDiagReservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_DIAGNOSIS_RESERVATION_NOT_FOUND));

        //* 예약 헤더 정보
        ReservationItem reservationItem = ReservationItem.builder()
                .reservationId(findNoncontactDiagReservation.getId())
                .signupDate(findNoncontactDiagReservation.getCreateDate())
                .patientName(findNoncontactDiagReservation.getUser().getUsername())
                .reservationDate(LocalDateTime.of(findNoncontactDiagReservation.getReservationDate(), findNoncontactDiagReservation.getReservationTime()))
                .build();

        Symptom findSymptom = findNoncontactDiagReservation.getSymptom();

        //* 예약 신청 세부 정보
        NoncontactDiagFormInfo noncontactDiagFormInfo = NoncontactDiagFormInfo.builder()
                .signupDate(findNoncontactDiagReservation.getCreateDate())
                .reservationDate(LocalDateTime.of(findNoncontactDiagReservation.getReservationDate(), findNoncontactDiagReservation.getReservationTime()))
                .department(doctor.getDepartment().getDepartmentName())
                .diagType(findNoncontactDiagReservation.getDiagType().getNoncontactDiagTypeName())
                .isPrescribedDrug(findSymptom.getIsPrescribedDrug())
                .prescribedDrug(findSymptom.getPrescribedDrug())
                .isAllergicSymptom(findSymptom.getIsAllergicSymptom())
                .allergicSymptom(findSymptom.getAllergicSymptom())
                .isInbornDisease(findSymptom.getIsInbornDisease())
                .inbornDisease(findSymptom.getInbornDisease())
                .additionalInformation(findSymptom.getAdditionalInformation())
                .build();

        return RequestReservationFormDto.builder()
                .reservationItem(reservationItem)
                .noncontactDiagFormInfo(noncontactDiagFormInfo)
                .build();
    }

    public void acceptReservation(Long doctorId, Long reservationId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DOCTOR_NOT_FOUND));

        NoncontactDiagReservation reservation = noncontactDiagReservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_DIAGNOSIS_RESERVATION_NOT_FOUND));

        // 예약 상태가 SIGN_UP인 경우만 수락 가능
        if (ReservationStatus.SIGN_UP.equals(reservation.getStatus())) {
            reservation.acceptReservation();
            noncontactDiagReservationRepository.save(reservation);
        } else {
            throw new BadRequestException(ErrorCode.INVALID_RESERVATION_STATUS);
        }
    }

    public void rejectReservation(Long doctorId, Long reservationId, RejectReservationRequest request) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DOCTOR_NOT_FOUND));

        NoncontactDiagReservation reservation = noncontactDiagReservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_DIAGNOSIS_RESERVATION_NOT_FOUND));

        // 예약 상태가 SIGN_UP인 경우만 거절 가능
        if (!ReservationStatus.SIGN_UP.equals(reservation.getStatus())) {
            throw new BadRequestException(ErrorCode.INVALID_RESERVATION_STATUS);
        }

        // 예약 상태를 거절로 변경하고 거절 사유 저장
        reservation.rejectReservation(request.getRejectionReason(), request.getAdditionalReason());
        noncontactDiagReservationRepository.save(reservation);
    }

    public PatientInfoDto getPatientInfo(Long doctorId, Long reservationId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DOCTOR_NOT_FOUND));

        NoncontactDiagReservation reservation = noncontactDiagReservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_DIAGNOSIS_RESERVATION_NOT_FOUND));

        User user = reservation.getUser();

        return PatientInfoDto.builder()
                .userId(user.getId())
                .userName(maskName(user.getUsername()))
                .age(convertBirthDateToAgeGroup(user.getBirthDate()))
                .gender(getGenderCode(user.getGender()))
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public String getGenderCode(Gender gender) {
        return gender.toString().substring(0, 1);  // 성별 첫 글자만 추출 (예: "M" or "F")
    }

    public String maskName(String name) {
        if (name == null || name.length() < 2) {
            return name;  // 이름이 너무 짧거나 null인 경우 그대로 반환
        }

        // 첫 글자만 남기고 나머지를 "OO"으로 마스킹
        String maskedName = name.substring(0, 1) + "OO";
        return maskedName;
    }

    // 생년월일을 나이대(10대, 20대 등)로 변환하는 메서드
    public static String convertBirthDateToAgeGroup(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDate birthDateLocalDate = LocalDate.parse(birthDate, formatter);
        int birthYear = birthDateLocalDate.getYear();
        int currentYear = LocalDate.now().getYear();

        if ((birthYear % 100) > (currentYear % 100)) {
            birthYear -= 100;  // 1900년대 출생자
        }

        int age = currentYear - birthYear + 1;
        return calculateAgeGroup(age);
    }

    // 나이대(10대, 20대 등) 계산 메서드
    private static String calculateAgeGroup(int age) {
        if (age < 10) {
            return "연령대 미상";
        }
        int ageGroup = age / 10 * 10;  // 10 단위로 나이대 계산
        return ageGroup + "대";
    }

    public Doctor registerDoctor(DoctorDTO doctorDTO) {
        // Fetch the Hospital and Department using their IDs
        Hospital hospital = hospitalRepository.findById(doctorDTO.getHospitalId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid hospital ID"));
        Department department = departmentRepository.findById(doctorDTO.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));

        // Create the Doctor entity
        Doctor newDoctor = Doctor.createDoctor(
                hospital,
                department,
                doctorDTO.getName(),
                doctorDTO.getEmail(),
                doctorDTO.getPassword(),
                doctorDTO.getIsLicenseCertificated(),
                doctorDTO.getProfileImagePath(),
                doctorDTO.getOneLiner(),
                doctorDTO.getExperience(),
                doctorDTO.getDiagTime()
        );

        return doctorRepository.save(newDoctor);
    }

    public boolean authenticateUser(String username, String password) {
        Doctor doctor = doctorRepository.findByName(username);
        if (doctor != null) {
            // 비밀번호 확인
            return doctor.getPassword().equals(password); // 입력된 비밀번호와 저장된 비밀번호 비교
        }
        return false;
    }

    public Doctor findByName(String username) {
        return doctorRepository.findByName(username);
    }
    // 인증 서비스 구축


    public String authenticateAndGenerateToken(String username, String password) {
        // 사용자 인증 로직
        boolean authenticate = authenticateUser(username, password);
        Doctor doctor = doctorRepository.findByName(username);
        if (authenticate) {
            // DB에서 조회한 사용자 ID를 사용하여 JWT 생성
            return jwtUtil.DoctorcreateJwt(username, doctor.getId().toString(), doctor.getOneLiner(), doctor.getEmail(), "doctor", 36000L); // 1시간 유효한 토큰 생성
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }
}