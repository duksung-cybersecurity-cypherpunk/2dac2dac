package dac2dac.doctect.health_list.dto.response.diagnosis;

import dac2dac.doctect.bootpay.entity.constant.PaymentType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoncontactDiagDetailInfo {

    private Boolean isPrescribedDrug;
    private Boolean isAllergicSymptom;
    private Boolean isInbornDisease;

    private Integer price;
    private String paymentType;
    private LocalDateTime approvalDate;

    @Builder
    public NoncontactDiagDetailInfo(Boolean isPrescribedDrug, Boolean isAllergicSymptom, Boolean isInbornDisease, Integer price, String paymentType, LocalDateTime approvalDate) {
        this.isPrescribedDrug = isPrescribedDrug;
        this.isAllergicSymptom = isAllergicSymptom;
        this.isInbornDisease = isInbornDisease;
        this.price = price;
        this.paymentType = paymentType;
        this.approvalDate = approvalDate;
    }
}