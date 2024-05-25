package dac2dac.doctect.noncontact_diag.Service;

import dac2dac.doctect.doctor.repository.DepartmentRepository;
import dac2dac.doctect.doctor.repository.DepartmentTagRepository;
import dac2dac.doctect.noncontact_diag.dto.response.DepartmentInfo;
import dac2dac.doctect.noncontact_diag.dto.response.DepartmentListDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoncontactDiagService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentTagRepository departmentTagRepository;

    public DepartmentListDto getDepartmentList() {
        List<DepartmentInfo> departmentInfoList = departmentRepository.findAll().stream()
            .map(d -> DepartmentInfo.builder()
                .departmentId(d.getId())
                .departmentName(d.getDepartmentName())
                .tags(departmentTagRepository.findByDepartmentId(d.getId()).stream()
                    .map(dt -> dt.getTag())
                    .collect(Collectors.toList()))
                .build()
            ).collect(Collectors.toList());

        return DepartmentListDto.builder()
            .cnt(departmentInfoList.size())
            .departmentInfoList(departmentInfoList)
            .build();
    }
}
