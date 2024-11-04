package dac2dac.doctect.doctor.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchMedicineRequestDto {

    @Size(min = 1, max = 20, message = "검색어는 1자 이상, 20자 이하로 입력해 주세요.")
    @NotBlank(message = "검색어는 필수 값입니다.")
    @Pattern(regexp = "^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]+$", message = "검색어는 한글, 영문, 숫자만 포함할 수 있습니다.")
    private String keyword;
}
