package dac2dac.doctect.doctor.service;

import dac2dac.doctect.bootpay.entity.PaymentInfo;
import dac2dac.doctect.common.constant.ErrorCode;
import dac2dac.doctect.common.error.exception.NotFoundException;
import dac2dac.doctect.doctor.dto.response.CompletedReservationListDto;
import dac2dac.doctect.doctor.dto.response.PrescriptionDto;
import dac2dac.doctect.doctor.dto.response.PrescriptionItem;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.doctor.repository.DoctorRepository;
import dac2dac.doctect.noncontact_diag.entity.NoncontactDiag;
import dac2dac.doctect.noncontact_diag.entity.Symptom;
import dac2dac.doctect.noncontact_diag.repository.NoncontactDiagRepository;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorNoncontactDiagService {

    private final DoctorRepository doctorRepository;
    private final NoncontactDiagRepository noncontactDiagRepository;

    public CompletedReservationListDto getCompletedReservation(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.DOCTOR_NOT_FOUND));

        List<PrescriptionItem> noncontactDiagList = noncontactDiagRepository.findByDoctorId(doctorId).stream()
            .map(n -> PrescriptionItem.builder()
                .noncontactDiagId(n.getId())
                .patientName(n.getUser().getUsername())
                .reservationDate(LocalDateTime.of(n.getDiagDate(), n.getDiagTime()))
                .build())
            .sorted(Comparator.comparing(PrescriptionItem::getReservationDate).reversed())
            .toList();

        return CompletedReservationListDto.builder()
            .totalCnt(noncontactDiagList.size())
            .completedReservationList(noncontactDiagList)
            .build();
    }

    public PrescriptionDto getPrescription(Long noncontactDiagId) {
        NoncontactDiag noncontactDiag = noncontactDiagRepository.findById(noncontactDiagId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NONCONTACT_DIAGNOSIS_NOT_FOUND));

        Symptom symptom = noncontactDiag.getNoncontactDiagReservation().getSymptom();
        PaymentInfo paymentInfo = noncontactDiag.getPaymentInfo();

        return PrescriptionDto.builder()
            .patientName(noncontactDiag.getUser().getUsername())
            .isAllergicSymptom(symptom.getIsAllergicSymptom())
            .isInbornDisease(symptom.getIsInbornDisease())
            .isPrescribedDrug(symptom.getIsPrescribedDrug())
            .paymentPrice(paymentInfo.getPrice())
            .paymentType(paymentInfo.getPaymentMethod().getPaymentType().getPaymentTypeName())
            .paymentAcceptedDate(paymentInfo.getCreateDate())
            .build();
    }
}
