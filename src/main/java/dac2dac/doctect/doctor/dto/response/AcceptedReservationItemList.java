package dac2dac.doctect.doctor.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AcceptedReservationItemList {

    private int totalCnt;
    private List<ReservationItem> acceptedReservationItemList = new ArrayList<>();

    @Builder
    public AcceptedReservationItemList(int totalCnt, List<ReservationItem> acceptedReservationItemList) {
        this.totalCnt = totalCnt;
        this.acceptedReservationItemList = acceptedReservationItemList;
    }
}
