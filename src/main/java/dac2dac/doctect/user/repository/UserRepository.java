package dac2dac.doctect.user.repository;

import dac2dac.doctect.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByUserId(Long userId);

}
