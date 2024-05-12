package dac2dac.doctect.health_list.repository;

import dac2dac.doctect.health_list.entity.HealthScreening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthScreeningRepository extends JpaRepository<HealthScreening, Long> {

    void deleteByUserId(Long userId);
}
