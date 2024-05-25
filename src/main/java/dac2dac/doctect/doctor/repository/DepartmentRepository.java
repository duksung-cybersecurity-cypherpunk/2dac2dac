package dac2dac.doctect.doctor.repository;


import dac2dac.doctect.doctor.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
