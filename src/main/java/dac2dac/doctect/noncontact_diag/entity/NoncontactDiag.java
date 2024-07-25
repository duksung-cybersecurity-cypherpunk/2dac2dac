package dac2dac.doctect.noncontact_diag.entity;

import dac2dac.doctect.bootpay.entity.PaymentInfo;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.noncontact_diag.entity.constant.DiagStatus;
import dac2dac.doctect.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
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

    private LocalDate diagDate;
    private LocalTime diagTime;

    @Enumerated(EnumType.STRING)
    private DiagStatus status;

    private boolean isReviewWrited;

}
