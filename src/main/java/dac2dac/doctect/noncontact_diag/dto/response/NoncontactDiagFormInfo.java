package dac2dac.doctect.noncontact_diag.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoncontactDiagFormInfo {

    private LocalDateTime signupDate;
    private LocalDateTime reservationDate;
    private String department;
    private String diagType;

    private Boolean isPrescribedDrug;
    private Boolean isAllergicSymptom;
    private Boolean isInbornDisease;

    private String prescribedDrug;
    private String allergicSymptom;
    private String inbornDisease;
    private String additionalInformation;

    @Builder
    public NoncontactDiagFormInfo(LocalDateTime signupDate, LocalDateTime reservationDate, String department, String diagType, Boolean isPrescribedDrug, Boolean isAllergicSymptom, Boolean isInbornDisease, String prescribedDrug, String allergicSymptom, String inbornDisease, String additionalInformation) {
        this.signupDate = signupDate;
        this.reservationDate = reservationDate;
        this.department = department;
        this.diagType = diagType;
        this.isPrescribedDrug = isPrescribedDrug;
        this.isAllergicSymptom = isAllergicSymptom;
        this.isInbornDisease = isInbornDisease;
        this.prescribedDrug = prescribedDrug;
        this.allergicSymptom = allergicSymptom;
        this.inbornDisease = inbornDisease;
        this.additionalInformation = additionalInformation;
    }
}
