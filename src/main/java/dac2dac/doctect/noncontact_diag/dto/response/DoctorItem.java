package dac2dac.doctect.noncontact_diag.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoctorItem {

    private Long id;

    private String name;
    private String hospitalName;

    private String profilePath;

    private Double averageRating;
    private Integer reviewCnt;

    private Boolean isOpen;
    private Integer todayOpenTime;
    private Integer todayCloseTime;

    @Builder
    public DoctorItem(Long id, String name, String hospitalName, String profilePath, Double averageRating, Integer reviewCnt, Boolean isOpen, Integer todayOpenTime, Integer todayCloseTime) {
        this.id = id;
        this.name = name;
        this.hospitalName = hospitalName;
        this.profilePath = profilePath;
        this.averageRating = averageRating;
        this.reviewCnt = reviewCnt;
        this.isOpen = isOpen;
        this.todayOpenTime = todayOpenTime;
        this.todayCloseTime = todayCloseTime;
    }
}
