package dac2dac.doctect.doctor.entity;

import dac2dac.doctect.agency.entity.Hospital;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

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

    public static Doctor registerDoctor(
        Hospital hospital,
        Department department,
        String name,
        String email,
        String password,
        String oneLiner) {
        Doctor doctor = new Doctor();
        doctor.hospital = hospital;
        doctor.department = department;
        doctor.name = name;
        doctor.email = email;
        doctor.password = password;
        doctor.oneLiner = oneLiner;
        doctor.isLicenseCertificated = true;
        return doctor;
    }

    public void checkJWT(String id, String username, String email, String oneLiner) {
        this.id = Long.parseLong(id);
        this.name = username;
        this.email = email;
        this.oneLiner = oneLiner;
    }
}
