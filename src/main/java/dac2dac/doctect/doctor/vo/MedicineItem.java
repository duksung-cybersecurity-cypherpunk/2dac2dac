package dac2dac.doctect.doctor.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dac2dac.doctect.doctor.entity.Medicine;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MedicineItem {

    @NotBlank(message = "ITEM_NAME 필수 값입니다.")
    @JsonProperty("ITEM_NAME")
    private String name;

    @NotBlank(message = "CHART 필수 값입니다.")
    @JsonProperty("CHART")
    private String chart;

    @NotBlank(message = "ITEM_IMAGE 필수 값입니다.")
    @JsonProperty("ITEM_IMAGE")
    private String imageUrl;

    @NotBlank(message = "CLASS_NAME 필수 값입니다.")
    @JsonProperty("CLASS_NAME")
    private String className;

    public Medicine toEntity() {
        return Medicine.builder()
                .name(name)
                .chart(chart)
                .imageUrl(imageUrl)
                .className(className)
                .build();
    }

}
