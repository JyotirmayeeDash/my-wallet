package com.wallet.repository;

import com.wallet.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PaymentMethod entity.
 */
@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
}
