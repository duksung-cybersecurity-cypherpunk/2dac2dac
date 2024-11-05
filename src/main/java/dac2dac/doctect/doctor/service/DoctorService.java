package dac2dac.doctect.doctor.service;

import static dac2dac.doctect.common.utils.DeidentificationUtils.convertBirthDateToAgeGroup;
import static dac2dac.doctect.common.utils.DeidentificationUtils.getGenderCode;
import static dac2dac.doctect.common.utils.DeidentificationUtils.maskName;

import dac2dac.doctect.bootpay.entity.PaymentInfo;
import dac2dac.doctect.bootpay.entity.PaymentMethod;
import dac2dac.doctect.bootpay.service.BootpayService;
import dac2dac.doctect.common.constant.ErrorCode;
import dac2dac.doctect.common.error.exception.BadRequestException;
import dac2dac.doctect.common.error.exception.NotFoundException;
import dac2dac.doctect.doctor.dto.request.DiagCompleteRequestDto;
import dac2dac.doctect.doctor.dto.request.RejectReservationRequest;
import dac2dac.doctect.doctor.dto.response.AcceptedReservationItemList;
import dac2dac.doctect.doctor.dto.response.PatientInfoDto;
import dac2dac.doctect.doctor.dto.response.RequestReservationFormDto;
import dac2dac.doctect.doctor.dto.response.RequestReservationItemList;
import dac2dac.doctect.doctor.dto.response.ReservationItem;
import dac2dac.doctect.doctor.dto.response.ReservationListDto;
import dac2dac.doctect.doctor.dto.response.TodayScheduledReservationDto;
import dac2dac.doctect.doctor.dto.response.UpcomingReservationDto;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.doctor.entity.Medicine;
import dac2dac.doctect.doctor.repository.DoctorRepository;
import dac2dac.doctect.doctor.repository.MedicineRepository;
import dac2dac.doctect.noncontact_diag.dto.response.NoncontactDiagFormInfo;
import dac2dac.doctect.noncontact_diag.entity.NoncontactDiag;
import dac2dac.doctect.noncontact_diag.entity.NoncontactDiagReservation;
import dac2dac.doctect.noncontact_diag.entity.NoncontactPrescription;
import dac2dac.doctect.noncontact_diag.entity.Symptom;
import dac2dac.doctect.noncontact_diag.entity.constant.ReservationStatus;
import dac2dac.doctect.noncontact_diag.repository.NoncontactDiagRepository;
import dac2dac.doctect.noncontact_diag.repository.NoncontactDiagReservationRepository;
import dac2dac.doctect.noncontact_diag.repository.NoncontactPrescriptionRepository;
import dac2dac.doctect.user.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final BootpayService bootpayService;
    private final NoncontactDiagReservationRepository noncontactDiagReservationRepository;
    private final NoncontactDiagRepository noncontactDiagRepository;
    private final DoctorRepository doctorRepository;
    private final NoncontactPrescriptionRepository noncontactPrescriptionRepository;
    private final MedicineRepository medicineRepository;

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
                .userId(r.getUser().getId())
                .reservationId(r.getId())
                .signupDate(r.getCreateDate())
                .patientName(maskName(r.getUser().getUsername()))
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
                .userId(r.getUser().getId())
                .reservationId(r.getId())
                .signupDate(r.getCreateDate())
                .patientName(maskName(r.getUser().getUsername()))
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
            .patientName(maskName(findNoncontactDiagReservation.getUser().getUsername()))
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

    public UpcomingReservationDto getUpcomingReservation(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.DOCTOR_NOT_FOUND));

        NoncontactDiagReservation reservation = noncontactDiagReservationRepository.findNearestReservationByDoctorIdAndStatus(doctorId, ReservationStatus.COMPLETE.name())
            .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_DIAGNOSIS_RESERVATION_NOT_FOUND));

        return UpcomingReservationDto.builder()
            .reservationId(reservation.getId())
            .reservationDate(LocalDateTime.of(reservation.getReservationDate(), reservation.getReservationTime()))
            .patientName(maskName(reservation.getUser().getUsername()))
            .build();
    }

    public TodayScheduledReservationDto getTodayReservation(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.DOCTOR_NOT_FOUND));

        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDate nowDate = LocalDate.now();

        // 오늘 예약 목록 가져오기
        List<NoncontactDiagReservation> reservationList = noncontactDiagReservationRepository.findByDoctorIdAndReservationDate(doctorId, nowDate)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_DIAGNOSIS_RESERVATION_NOT_FOUND));

        // Scheduled 예약 필터링 (현재 시간 이후 & 예약 상태 Complete)
        List<ReservationItem> scheduledReservation = reservationList.stream()
            .filter(reservation -> LocalDateTime.of(reservation.getReservationDate(), reservation.getReservationTime()).isAfter(nowDateTime)
                && reservation.getStatus().equals(ReservationStatus.COMPLETE))
            .map(r -> ReservationItem.builder()
                .userId(r.getUser().getId())
                .patientName(maskName(r.getUser().getUsername()))
                .reservationId(r.getId())
                .reservationDate(LocalDateTime.of(r.getReservationDate(), r.getReservationTime()))
                .build())
            .sorted(Comparator.comparing(ReservationItem::getReservationDate))
            .collect(Collectors.toList());

        return TodayScheduledReservationDto.builder()
            .totalCnt(scheduledReservation.size())
            .scheduledReservationItemList(scheduledReservation)
            .build();
    }

    public void completeReservation(DiagCompleteRequestDto request, Long doctorId, Long reservationId) throws Exception {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.DOCTOR_NOT_FOUND));

        NoncontactDiagReservation reservation = noncontactDiagReservationRepository.findById(reservationId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_DIAGNOSIS_RESERVATION_NOT_FOUND));

        // 예약 시간이 현재 시간보다 이후일 경우 예외 처리
        LocalDateTime reservationDateTime = LocalDateTime.of(reservation.getReservationDate(), reservation.getReservationTime());
        LocalDateTime now = LocalDateTime.now();

        if (reservationDateTime.isAfter(now)) {
            throw new BadRequestException(ErrorCode.RESERVATION_NOT_STARTED);
        }

        // 결제 수행
        PaymentMethod paymentMethod = reservation.getPaymentMethod();
        PaymentInfo paymentInfo = bootpayService.payWithBillingKey(paymentMethod, request.getPrice());

        // 진료 완료된 건에 대해서는 진료 테이블에 저장
        NoncontactDiag noncontactDiag = NoncontactDiag.builder()
            .user(reservation.getUser())
            .doctor(reservation.getDoctor())
            .noncontactDiagReservation(reservation)
            .paymentInfo(paymentInfo)
            .doctorOpinion(request.getDoctorOpinion())
            .diagDate(reservation.getReservationDate())
            .diagTime(reservation.getReservationTime())
            .build();

        noncontactDiagRepository.save(noncontactDiag);

        // 처방 의약품
        NoncontactPrescription prescription = NoncontactPrescription.builder()
            .user(reservation.getUser())
            .noncontactDiag(noncontactDiag)
            .prescriptionDrugList(new ArrayList<>())
            .build();

        request.getMedicineIds().forEach(id -> {
            Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DOCTOR_NOT_FOUND));
            prescription.addPrescriptionDrug(medicine);
        });

        noncontactPrescriptionRepository.save(prescription);
    }
}