package dac2dac.doctect.noncontact_diag.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoctorReview {

    LocalDateTime createdAt;
    Integer rating;
    List<String> reviewTagList;
    String contents;

    @Builder
    public DoctorReview(LocalDateTime createdAt, Integer rating, List<String> reviewTagList, String contents) {
        this.createdAt = createdAt;
        this.rating = rating;
        this.reviewTagList = reviewTagList;
        this.contents = contents;
    }
}
