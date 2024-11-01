package dac2dac.doctect.doctor.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PrescriptionDto {

    private String patientName;

    private Boolean isPrescribedDrug;
    private Boolean isAllergicSymptom;
    private Boolean isInbornDisease;

    private String doctorOpinion;

    private Integer paymentPrice;
    private String paymentType;
    private LocalDateTime paymentAcceptedDate;

    @Builder
    public PrescriptionDto(String patientName, Boolean isPrescribedDrug, Boolean isAllergicSymptom, Boolean isInbornDisease, String doctorOpinion, Integer paymentPrice, String paymentType,
        LocalDateTime paymentAcceptedDate) {
        this.patientName = patientName;
        this.isPrescribedDrug = isPrescribedDrug;
        this.isAllergicSymptom = isAllergicSymptom;
        this.isInbornDisease = isInbornDisease;
        this.doctorOpinion = doctorOpinion;
        this.paymentPrice = paymentPrice;
        this.paymentType = paymentType;
        this.paymentAcceptedDate = paymentAcceptedDate;
    }
}
