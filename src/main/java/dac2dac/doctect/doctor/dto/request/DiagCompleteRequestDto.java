package dac2dac.doctect.doctor.dto.request;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiagCompleteRequestDto {

    private Integer price;
    private List<PrescriptionMedicineItem> medicineList = new ArrayList<>();
    private String doctorOpinion;
}
