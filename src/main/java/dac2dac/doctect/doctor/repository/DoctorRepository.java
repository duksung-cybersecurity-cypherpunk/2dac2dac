package dac2dac.doctect.doctor.repository;

import dac2dac.doctect.doctor.entity.Doctor;
import java.util.List;

import dac2dac.doctect.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT d FROM Doctor d WHERE d.department.id = :departmentId")
    List<Doctor> findByDepartmentId(@Param("departmentId") Long departmentId);

    Doctor findByName(String username);
    Doctor findByEmail(String email);

}
