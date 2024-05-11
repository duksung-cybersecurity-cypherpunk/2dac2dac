package dac2dac.doctect.health_list.dto.request;

import dac2dac.doctect.health_list.entity.ContactDiag;
import dac2dac.doctect.health_list.entity.constant.DiagType;
import dac2dac.doctect.user.entity.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiagnosisDto {

    private String agencyName;
    private LocalDateTime diagDate;

    private DiagType diagType;

    private Integer prescription_cnt;
    private Integer medication_cnt;
    private Integer visit_days;

    @Builder
    public DiagnosisDto(String agencyName, LocalDateTime diagDate, DiagType diagType, Integer prescription_cnt, Integer medication_cnt, Integer visit_days) {
        this.agencyName = agencyName;
        this.diagDate = diagDate;
        this.diagType = diagType;
        this.prescription_cnt = prescription_cnt;
        this.medication_cnt = medication_cnt;
        this.visit_days = visit_days;
    }

    public ContactDiag toEntity(User user) {
        return ContactDiag.builder()
            .user(user)
            .agencyName(agencyName)
            .diagDate(diagDate)
            .diagType(diagType)
            .prescription_cnt(prescription_cnt)
            .medication_cnt(medication_cnt)
            .visit_days(visit_days)
            .build();
    }
}
