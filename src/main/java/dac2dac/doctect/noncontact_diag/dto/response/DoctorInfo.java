package dac2dac.doctect.noncontact_diag.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoctorInfo {

    private String name;
    private String hospitalName;

    private String profilePath;

    private Boolean isOpen;
    private Integer todayOpenTime;
    private Integer todayCloseTime;

    private Double averageRating;
    private Integer reviewCnt;

    private List<Top3ReviewTagInfo> top3ReviewTagList;

    @Builder
    public DoctorInfo(String name, String hospitalName, String profilePath, Boolean isOpen, Integer todayOpenTime, Integer todayCloseTime, Double averageRating, Integer reviewCnt,
        List<Top3ReviewTagInfo> top3ReviewTagList) {
        this.name = name;
        this.hospitalName = hospitalName;
        this.profilePath = profilePath;
        this.isOpen = isOpen;
        this.todayOpenTime = todayOpenTime;
        this.todayCloseTime = todayCloseTime;
        this.averageRating = averageRating;
        this.reviewCnt = reviewCnt;
        this.top3ReviewTagList = top3ReviewTagList;
    }
}
