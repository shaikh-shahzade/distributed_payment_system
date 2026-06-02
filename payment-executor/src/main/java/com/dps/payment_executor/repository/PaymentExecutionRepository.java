package com.dps.payment_executor.repository;

import com.dps.payment_executor.entity.PaymentExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentExecutionRepository extends JpaRepository<PaymentExecution, Long> {

	Optional<PaymentExecution> findByPaymentOrderId(String paymentOrderId);

	Optional<PaymentExecution> findByRazorpayOrderId(String razorpayOrderId);

	List<PaymentExecution> findByStatus(PaymentExecution.ExecutionStatus status);

	List<PaymentExecution> findByStatusAndRetryCountLessThan(PaymentExecution.ExecutionStatus status, Integer maxRetry);

	List<PaymentExecution> findByStatusAndCreatedAtBefore(PaymentExecution.ExecutionStatus status,
			LocalDateTime timestamp);

	boolean existsByPaymentOrderId(String paymentOrderId);
}
