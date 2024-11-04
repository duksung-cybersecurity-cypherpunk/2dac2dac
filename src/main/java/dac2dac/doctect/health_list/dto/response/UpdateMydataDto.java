package dac2dac.doctect.health_list.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMydataDto {

    LocalDateTime updateTime;

    @Builder
    public UpdateMydataDto(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
