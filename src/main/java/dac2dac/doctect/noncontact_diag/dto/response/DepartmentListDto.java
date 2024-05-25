package dac2dac.doctect.noncontact_diag.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepartmentListDto {

    private Integer cnt;
    private List<DepartmentInfo> departmentInfoList = new ArrayList<>();

    @Builder
    public DepartmentListDto(Integer cnt, List<DepartmentInfo> departmentInfoList) {
        this.cnt = cnt;
        this.departmentInfoList = departmentInfoList;
    }
}
