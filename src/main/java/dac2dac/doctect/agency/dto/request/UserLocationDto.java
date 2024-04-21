package dac2dac.doctect.agency.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLocationDto {

    @NotBlank(message = "longitude는 필수 값입니다.")
    private Double longitude;

    @NotBlank(message = "latitude는 필수 값입니다.")
    private Double latitude;
}
