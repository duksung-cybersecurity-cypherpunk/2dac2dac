package dac2dac.doctect.noncontact_diag.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Symptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isPrescribedDrug;
    private Boolean isAllergicSymptom;
    private Boolean isInbornDisease;

    @Lob
    private String prescribedDrug;

    @Lob
    private String allergicSymptom;

    @Lob
    private String inbornDisease;

    @Lob
    private String additionalInformation;

    private String symptomImage1;
    private String symptomImage2;
    private String symptomImage3;
    private String symptomImage4;
    private String symptomImage5;

    @Builder
    public Symptom(Boolean isPrescribedDrug, Boolean isAllergicSymptom, Boolean isInbornDisease, String prescribedDrug, String allergicSymptom, String inbornDisease,
        String additionalInformation,
        String symptomImage1, String symptomImage2, String symptomImage3, String symptomImage4, String symptomImage5) {
        this.isPrescribedDrug = isPrescribedDrug;
        this.isAllergicSymptom = isAllergicSymptom;
        this.isInbornDisease = isInbornDisease;
        this.prescribedDrug = prescribedDrug;
        this.allergicSymptom = allergicSymptom;
        this.inbornDisease = inbornDisease;
        this.additionalInformation = additionalInformation;
        this.symptomImage1 = symptomImage1;
        this.symptomImage2 = symptomImage2;
        this.symptomImage3 = symptomImage3;
        this.symptomImage4 = symptomImage4;
        this.symptomImage5 = symptomImage5;
    }
}
