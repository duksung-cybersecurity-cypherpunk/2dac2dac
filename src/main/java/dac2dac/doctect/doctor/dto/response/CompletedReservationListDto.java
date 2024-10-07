package dac2dac.doctect.doctor.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompletedReservationListDto {

    private int totalCnt;
    private List<UpcomingReservationDto> completedReservationList = new ArrayList<>();

    @Builder
    public CompletedReservationListDto(int totalCnt, List<UpcomingReservationDto> completedReservationList) {
        this.totalCnt = totalCnt;
        this.completedReservationList = completedReservationList;
    }
}
