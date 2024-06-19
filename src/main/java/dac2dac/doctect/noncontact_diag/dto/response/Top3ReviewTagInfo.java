package dac2dac.doctect.noncontact_diag.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Top3ReviewTagInfo {

    private String reviewTagName;
    private Double percentage;
    private Integer cnt;

    @Builder
    public Top3ReviewTagInfo(String reviewTagName, Double percentage, Integer cnt) {
        this.reviewTagName = reviewTagName;
        this.percentage = percentage;
        this.cnt = cnt;
    }
}
