package dac2dac.doctect.keypad.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IntegrityId {

    private String id;
    private Long timestamp;
    private String hmac;

    @Builder
    public IntegrityId(String id, Long timestamp, String hmac) {
        this.id = id;
        this.timestamp = timestamp;
        this.hmac = hmac;
    }
}
