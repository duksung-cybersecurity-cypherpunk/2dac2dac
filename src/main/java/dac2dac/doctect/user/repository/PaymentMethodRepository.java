package dac2dac.doctect.user.repository;

import dac2dac.doctect.user.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    Optional<PaymentMethod> findPaymentMethodByBillingKey(String billingKey);
}
