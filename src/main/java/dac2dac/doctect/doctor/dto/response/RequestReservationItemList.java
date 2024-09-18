package dac2dac.doctect.doctor.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestReservationItemList {

    private int totalCnt;
    private List<ReservationItem> requestReservationItemList = new ArrayList<>();

    @Builder
    public RequestReservationItemList(int totalCnt, List<ReservationItem> requestReservationItemList) {
        this.totalCnt = totalCnt;
        this.requestReservationItemList = requestReservationItemList;
    }
}
