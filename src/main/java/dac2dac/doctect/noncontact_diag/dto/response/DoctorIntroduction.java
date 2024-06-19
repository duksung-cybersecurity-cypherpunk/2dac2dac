package dac2dac.doctect.noncontact_diag.dto.response;

import dac2dac.doctect.common.entity.DiagTime;
import jakarta.persistence.Embedded;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoctorIntroduction {

    private String department;

    private String oneLiner;
    private String experience;

    @Embedded
    private DiagTime diagTime;

    private Long hospitalId;
    private String hospitalName;
    private String hospitalAddress;
    private String hospitalThumnail;

    @Builder
    public DoctorIntroduction(String department, String oneLiner, String experience, DiagTime diagTime, Long hospitalId, String hospitalName, String hospitalAddress, String hospitalThumnail) {
        this.department = department;
        this.oneLiner = oneLiner;
        this.experience = experience;
        this.diagTime = diagTime;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.hospitalThumnail = hospitalThumnail;
    }
}
