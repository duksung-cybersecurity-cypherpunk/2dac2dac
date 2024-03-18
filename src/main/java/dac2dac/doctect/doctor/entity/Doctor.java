package dac2dac.doctect.doctor.entity;

import dac2dac.doctect.agency.entity.Hospital;
import dac2dac.doctect.common.entity.BaseEntity;
import dac2dac.doctect.doctor.entity.constant.Department;
import dac2dac.doctect.user.entity.constant.SocialType;
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
public class Doctor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    private String name;
    private String email;
    private SocialType socialType;
    private String code;

    @Lob
    private String oneLiner;

    private Department department;

    @Lob
    private String experience;

    private Boolean isLicenseCertificated;
    private String profileImagePath;

}
