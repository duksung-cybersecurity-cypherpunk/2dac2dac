package dac2dac.doctect.doctor.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DiagCompleteRequestDto {

    private Integer price;
    private List<Long> medicineIds;
    private String doctorOpinion;
}
