package dac2dac.doctect.health_list.dto.request;

import dac2dac.doctect.health_list.entity.Prescription;
import dac2dac.doctect.user.entity.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PrescriptionDto {

    private List<DrugDto> drugDtoList = new ArrayList<>();

    private String agencyName;
    private LocalDateTime treatDate;

    @Builder
    public PrescriptionDto(List<DrugDto> drugDtoList, String agencyName, LocalDateTime treatDate) {
        this.drugDtoList = drugDtoList;
        this.agencyName = agencyName;
        this.treatDate = treatDate;
    }

    public Prescription toEntity(User user) {
        return Prescription.builder()
            .user(user)
            .agencyName(agencyName)
            .treatDate(treatDate)
            .build();
    }
}
