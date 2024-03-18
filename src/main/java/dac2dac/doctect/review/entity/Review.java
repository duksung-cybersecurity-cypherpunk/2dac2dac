package dac2dac.doctect.review.entity;

import dac2dac.doctect.agency.entity.Hospital;
import dac2dac.doctect.common.entity.BaseEntity;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.noncontact_diag.entity.NoncontactDiag;
import dac2dac.doctect.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "noncontact_diag_id")
    private NoncontactDiag noncontactDiag;

    private Double rating;

    @Lob
    private String content;

}
