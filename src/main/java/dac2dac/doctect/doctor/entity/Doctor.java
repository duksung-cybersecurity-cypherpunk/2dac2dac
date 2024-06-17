package dac2dac.doctect.doctor.entity;

import dac2dac.doctect.agency.entity.Hospital;
import dac2dac.doctect.common.entity.DiagTime;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    private String name;
    private String email;
    private String password;

    private Double avarageRating;

    private Boolean isLicenseCertificated;
    private String profileImagePath;

    @Column(columnDefinition = "TEXT")
    private String oneLiner;

    @Lob
    private String experience;

    @Embedded
    private DiagTime diagTime;
}
