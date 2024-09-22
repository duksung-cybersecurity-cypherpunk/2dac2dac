package dac2dac.doctect.doctor.dto;

import dac2dac.doctect.common.entity.DiagTime;

public class DoctorDTO {
    private Long hospitalId;
    private Long departmentId;
    private String name;
    private String email;
    private String password;
    private Boolean isLicenseCertificated;
    private String profileImagePath;
    private String oneLiner;
    private String experience;
    private DiagTime diagTime;

    // Getters
    public Long getHospitalId() {
        return hospitalId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getIsLicenseCertificated() {
        return isLicenseCertificated;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public String getOneLiner() {
        return oneLiner;
    }

    public String getExperience() {
        return experience;
    }

    public DiagTime getDiagTime() {
        return diagTime;
    }
}
