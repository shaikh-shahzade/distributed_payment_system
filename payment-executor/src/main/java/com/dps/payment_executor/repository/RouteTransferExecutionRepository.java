package com.dps.payment_executor.repository;

import com.dps.payment_executor.entity.RouteTransferExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteTransferExecutionRepository extends JpaRepository<RouteTransferExecution, Long> {

	Optional<RouteTransferExecution> findByTransferId(String transferId);

	Optional<RouteTransferExecution> findByRazorpayTransferId(String razorpayTransferId);

	boolean existsByTransferId(String transferId);
}
