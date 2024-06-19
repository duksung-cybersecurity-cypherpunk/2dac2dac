package dac2dac.doctect.noncontact_diag.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoctorReviewList {

    private Integer cnt;
    private List<DoctorReview> doctorReviewList = new ArrayList<>();

    @Builder
    public DoctorReviewList(Integer cnt, List<DoctorReview> doctorReviewList) {
        this.cnt = cnt;
        this.doctorReviewList = doctorReviewList;
    }
}
