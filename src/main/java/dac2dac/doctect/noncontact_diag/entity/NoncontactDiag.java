package dac2dac.doctect.noncontact_diag.entity;

import dac2dac.doctect.bootpay.entity.PaymentInfo;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoncontactDiag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noncontact_diag_reservation_id")
    private NoncontactDiagReservation noncontactDiagReservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_info_id")
    private PaymentInfo paymentInfo;

    private String doctorOpinion;
    private LocalDate diagDate;
    private LocalTime diagTime;

    @Builder
    public NoncontactDiag(User user, Doctor doctor, NoncontactDiagReservation noncontactDiagReservation, PaymentInfo paymentInfo, String doctorOpinion, LocalDate diagDate,
        LocalTime diagTime) {
        this.user = user;
        this.doctor = doctor;
        this.noncontactDiagReservation = noncontactDiagReservation;
        this.paymentInfo = paymentInfo;
        this.doctorOpinion = doctorOpinion;
        this.diagDate = diagDate;
        this.diagTime = diagTime;
    }

    public static NoncontactDiag createNoncontactDiag(User user, Doctor doctor, NoncontactDiagReservation noncontactDiagReservation, PaymentInfo paymentInfo, LocalDate diagDate, LocalTime diagTime) {
        NoncontactDiag noncontactDiag = new NoncontactDiag();
        noncontactDiag.user = user;
        noncontactDiag.doctor = doctor;
        noncontactDiag.noncontactDiagReservation = noncontactDiagReservation;
        noncontactDiag.paymentInfo = paymentInfo;
        noncontactDiag.diagDate = diagDate;
        noncontactDiag.diagTime = diagTime;
        return noncontactDiag;
    }

}
