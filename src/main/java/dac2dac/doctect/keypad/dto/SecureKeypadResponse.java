package dac2dac.doctect.keypad.dto;

import dac2dac.doctect.keypad.entity.IntegrityId;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SecureKeypadResponse {

    private IntegrityId integrityId;
    private List<String> keys;
    private String image;
    private Integer numberOfRow;
    private Integer numberOfCol;

    @Builder
    public SecureKeypadResponse(IntegrityId integrityId, List<String> keys, String image, Integer numberOfRow, Integer numberOfCol) {
        this.integrityId = integrityId;
        this.keys = keys;
        this.image = image;
        this.numberOfRow = numberOfRow;
        this.numberOfCol = numberOfCol;
    }
}