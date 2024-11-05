package dac2dac.doctect.agency.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchCriteria {

    @Min(-180)
    @Max(180)
    @NotNull(message = "longitude는 필수 값입니다.")
    private Double longitude;

    @Min(-90)
    @Max(90)
    @NotNull(message = "latitude는 필수 값입니다.")
    private Double latitude;

    private boolean hospital; // 병원
    private boolean pharmacy; // 약국
    private boolean er; // 응급실
}
