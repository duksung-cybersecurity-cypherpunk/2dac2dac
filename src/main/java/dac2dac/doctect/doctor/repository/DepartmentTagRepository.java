package dac2dac.doctect.doctor.repository;

import dac2dac.doctect.doctor.entity.DepartmentTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentTagRepository extends JpaRepository<DepartmentTag, Long> {

    List<DepartmentTag> findByDepartmentId(Long id);
}
