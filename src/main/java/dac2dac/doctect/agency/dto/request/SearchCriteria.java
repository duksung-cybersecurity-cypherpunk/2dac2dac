package dac2dac.doctect.agency.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchCriteria {

    @NotBlank(message = "longitude는 필수 값입니다.")
    private double longitude;
    @NotBlank(message = "latitude는 필수 값입니다.")
    private double latitude;

    private String keyword; // 검색어

    private boolean hospital; // 병원
    private boolean pharmacy; // 약국
    private boolean er; // 응급실

    private boolean nowOpen; // 현재 영업중
    private boolean allYearRound; // 연중 무휴
    private boolean midnightOpen; // 야간 영업

    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
    private boolean sun;
}
