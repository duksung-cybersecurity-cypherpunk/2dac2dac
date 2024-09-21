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

    private Boolean isLicenseCertificated;
    private String profileImagePath;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String oneLiner;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String experience;

    @Embedded
    private DiagTime diagTime;

    // Remove these two fields:
    // private Long hospitalId;
    // private Long departmentId;

    // Modify getters if needed
    public Long getHospitalId() {
        return hospital != null ? hospital.getId() : null; // Handle null case
    }

    public Long getDepartmentId() {
        return department != null ? department.getId() : null; // Handle null case
    }

    public static Doctor createDoctor(
            Hospital hospital,
            Department department,
            String name,
            String email,
            String password,
            Boolean isLicenseCertificated,
            String profileImagePath,
            String oneLiner,
            String experience,
            DiagTime diagTime) {
        Doctor doctor = new Doctor();
        doctor.hospital = hospital;
        doctor.department = department;
        doctor.name = name;
        doctor.email = email;
        doctor.password = password;
        doctor.isLicenseCertificated = isLicenseCertificated;
        doctor.profileImagePath = profileImagePath;
        doctor.oneLiner = oneLiner;
        doctor.experience = experience;
        doctor.diagTime = diagTime;
        return doctor;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setId(String id) {
        this.id = Long.parseLong(id);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setOneLiner(String oneLiner) {
        this.oneLiner = oneLiner;
    }

}
