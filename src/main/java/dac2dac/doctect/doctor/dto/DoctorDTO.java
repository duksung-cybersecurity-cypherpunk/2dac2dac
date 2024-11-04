package dac2dac.doctect.doctor.dto;

import dac2dac.doctect.common.entity.DiagTime;
import lombok.Getter;

@Getter
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
}
