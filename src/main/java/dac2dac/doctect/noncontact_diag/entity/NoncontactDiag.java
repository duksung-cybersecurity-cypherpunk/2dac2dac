package dac2dac.doctect.noncontact_diag.entity;

import dac2dac.doctect.agency.entity.Hospital;
import dac2dac.doctect.common.entity.BaseEntity;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.noncontact_diag.entity.constant.NoncontactDiagType;
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
import java.sql.Time;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoncontactDiag extends BaseEntity {

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
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    private LocalDate diagDate;
    private Time diagTime;

    @Enumerated(EnumType.STRING)
    private NoncontactDiagType status;

    private boolean isReviewWrited;
}
