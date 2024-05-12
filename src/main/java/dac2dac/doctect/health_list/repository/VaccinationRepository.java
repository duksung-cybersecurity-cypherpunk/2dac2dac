package dac2dac.doctect.health_list.repository;

import dac2dac.doctect.health_list.entity.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {

    void deleteByUserId(Long userId);
}
