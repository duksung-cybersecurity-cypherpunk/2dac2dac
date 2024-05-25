package dac2dac.doctect.doctor.entity;

import dac2dac.doctect.agency.entity.Hospital;
import dac2dac.doctect.common.entity.BaseEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    private String name;
    private String email;
    private String password;

    private Double avarageRating;

    private Boolean isLicenseCertificated;
    private String profileImagePath;

    @Lob
    private String oneLiner;

    @Lob
    private String experience;

    protected Integer diagTimeMonOpen;
    protected Integer diagTimeMonClose;

    protected Integer diagTimeTuesOpen;
    protected Integer diagTimeTuesClose;

    protected Integer diagTimeWedsOpen;
    protected Integer diagTimeWedsClose;

    protected Integer diagTimeThursOpen;
    protected Integer diagTimeThursClose;

    protected Integer diagTimeFriOpen;
    protected Integer diagTimeFriClose;

    protected Integer diagTimeSatOpen;
    protected Integer diagTimeSatClose;

    protected Integer diagTimeSunOpen;
    protected Integer diagTimeSunClose;

    protected Integer diagTimeHolidayOpen;
    protected Integer diagTimeHolidayClose;
}
