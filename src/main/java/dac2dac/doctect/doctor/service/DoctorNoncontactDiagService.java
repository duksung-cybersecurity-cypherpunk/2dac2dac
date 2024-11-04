package dac2dac.doctect.doctor.service;

import static dac2dac.doctect.common.utils.DeidentificationUtils.maskName;

import dac2dac.doctect.bootpay.entity.PaymentInfo;
import dac2dac.doctect.common.constant.ErrorCode;
import dac2dac.doctect.common.error.exception.NotFoundException;
import dac2dac.doctect.doctor.dto.response.CompletedDiagItem;
import dac2dac.doctect.doctor.dto.response.CompletedReservationListDto;
import dac2dac.doctect.doctor.dto.response.PrescriptionDto;
import dac2dac.doctect.doctor.dto.response.ToBeCompletedItem;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.doctor.repository.DoctorRepository;
import dac2dac.doctect.noncontact_diag.entity.NoncontactDiag;
import dac2dac.doctect.noncontact_diag.entity.NoncontactDiagReservation;
import dac2dac.doctect.noncontact_diag.entity.NoncontactPrescription;
import dac2dac.doctect.noncontact_diag.entity.Symptom;
import dac2dac.doctect.noncontact_diag.entity.constant.ReservationStatus;
import dac2dac.doctect.noncontact_diag.repository.NoncontactDiagRepository;
import dac2dac.doctect.noncontact_diag.repository.NoncontactDiagReservationRepository;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dac2dac.doctect.noncontact_diag.repository.NoncontactPrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorNoncontactDiagService {

    private final DoctorRepository doctorRepository;
    private final NoncontactDiagRepository noncontactDiagRepository;
    private final NoncontactDiagReservationRepository noncontactDiagReservationRepository;
    private final NoncontactPrescriptionRepository noncontactPrescriptionRepository;

    public CompletedReservationListDto getCompletedReservation(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.DOCTOR_NOT_FOUND));

        LocalDateTime nowDateTime = LocalDateTime.now();

        // 처방전까지 작성 완료된 예약 내역
        List<NoncontactDiag> noncontactDiagList = noncontactDiagRepository.findByDoctorId(doctorId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.DOCTOR_NOT_FOUND));

        List<CompletedDiagItem> completedReservationList = noncontactDiagList.stream()
            .map(n -> CompletedDiagItem.builder()
                .noncontactDiagId(n.getId())
                .patientName(maskName(n.getUser().getUsername()))
                .reservationDate(LocalDateTime.of(n.getDiagDate(), n.getDiagTime()))
                .build())
            .sorted(Comparator.comparing(CompletedDiagItem::getReservationDate).reversed())
            .toList();

        // 진료까지만 완료된 예약 내역 (처방전 작성 필요)
        List<NoncontactDiagReservation> noncontactDiagReservationList = noncontactDiagReservationRepository.findByDoctorIdAndStatus(doctorId, ReservationStatus.COMPLETE)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_DIAGNOSIS_RESERVATION_NOT_FOUND));

        // 진료 완료 예약에 해당하는 진료 예약 아이디 (필터링 용)
        Set<Long> completedReservationIds = noncontactDiagList.stream()
            .map(n -> n.getNoncontactDiagReservation().getId())
            .collect(Collectors.toSet());

        List<ToBeCompletedItem> toBeCompleteReservationList = noncontactDiagReservationList.stream()
            .filter(reservation -> LocalDateTime.of(reservation.getReservationDate(), reservation.getReservationTime()).isBefore(nowDateTime)
                && !completedReservationIds.contains(reservation.getId()))
            .map(r -> ToBeCompletedItem.builder()
                .patientName(maskName(r.getUser().getUsername()))
                .reservationId(r.getId())
                .reservationDate(LocalDateTime.of(r.getReservationDate(), r.getReservationTime()))
                .build())
            .sorted(Comparator.comparing(ToBeCompletedItem::getReservationDate))
            .collect(Collectors.toList());

        return CompletedReservationListDto.builder()
            .totalCnt(completedReservationList.size() + toBeCompleteReservationList.size())
            .completedReservationList(completedReservationList)
            .toBeCompleteReservationList(toBeCompleteReservationList)
            .build();
    }

    public PrescriptionDto getPrescription(Long noncontactDiagId) {
        NoncontactDiag noncontactDiag = noncontactDiagRepository.findById(noncontactDiagId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_DIAGNOSIS_NOT_FOUND));

        Symptom symptom = noncontactDiag.getNoncontactDiagReservation().getSymptom();
        PaymentInfo paymentInfo = noncontactDiag.getPaymentInfo();

        NoncontactPrescription noncontactPrescription = noncontactPrescriptionRepository.findByNoncontactDiagId(noncontactDiagId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_PRESCRIPTION_NOT_FOUND));

        return PrescriptionDto.builder()
            .patientName(maskName((noncontactDiag.getUser().getUsername())))
            .isAllergicSymptom(symptom.getIsAllergicSymptom())
            .isInbornDisease(symptom.getIsInbornDisease())
            .isPrescribedDrug(symptom.getIsPrescribedDrug())
            .medicineList(noncontactPrescription.getPrescriptionDrugList())
            .doctorOpinion(noncontactDiag.getDoctorOpinion())
            .paymentPrice(paymentInfo.getPrice())
            .paymentType(paymentInfo.getPaymentMethod().getPaymentType().getPaymentTypeName())
            .paymentAcceptedDate(paymentInfo.getCreateDate())
            .build();
    }

}
