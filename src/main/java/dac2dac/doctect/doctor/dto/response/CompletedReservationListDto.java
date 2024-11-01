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
    private List<CompletedDiagItem> completedReservationList = new ArrayList<>();
    private List<ToBeCompletedItem> toBeCompleteReservationList = new ArrayList<>();

    @Builder
    public CompletedReservationListDto(int totalCnt, List<CompletedDiagItem> completedReservationList, List<ToBeCompletedItem> toBeCompleteReservationList) {
        this.totalCnt = totalCnt;
        this.completedReservationList = completedReservationList;
        this.toBeCompleteReservationList = toBeCompleteReservationList;
    }
}
