package dac2dac.doctect.noncontact_diag.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepartmentInfo {

    private Long departmentId;
    private String departmentName;

    private List<String> tags;

    @Builder
    public DepartmentInfo(Long departmentId, String departmentName, List<String> tags) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.tags = tags;
    }
}