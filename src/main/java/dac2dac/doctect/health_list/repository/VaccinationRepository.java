package dac2dac.doctect.health_list.repository;

import dac2dac.doctect.health_list.entity.Vaccination;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {

    void deleteByUserId(Long userId);

    List<Vaccination> findByUserId(Long userId);
}
