package com.dps.payment_executor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_executions", indexes = {
		@Index(name = "idx_payment_order_id", columnList = "payment_order_id", unique = true),
		@Index(name = "idx_razorpay_order_id", columnList = "razorpay_order_id"),
		@Index(name = "idx_status", columnList = "status"),
		@Index(name = "idx_created_at", columnList = "created_at") })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentExecution {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "payment_order_id", nullable = false, unique = true, length = 100)
	private String paymentOrderId;

	@Column(name = "checkout_id", nullable = false, length = 100)
	private String checkoutId;

	@Column(name = "seller_id", nullable = false, length = 100)
	private String sellerId;

	@Column(name = "buyer_id", nullable = false, length = 100)
	private String buyerId;

	@Column(name = "amount", nullable = false, precision = 19, scale = 2)
	private BigDecimal amount;

	@Column(name = "currency", nullable = false, length = 3)
	private String currency;

	@Column(name = "razorpay_order_id", length = 100)
	private String razorpayOrderId;

	@Column(name = "razorpay_payment_id", length = 100)
	private String razorpayPaymentId;

	@Column(name = "razorpay_signature", length = 500)
	private String razorpaySignature;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private ExecutionStatus status;

	@Column(name = "retry_count", nullable = false)
	private Integer retryCount;

	@Column(name = "failure_reason", length = 1000)
	private String failureReason;

	@Column(name = "webhook_received", nullable = false)
	private Boolean webhookReceived;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "completed_at")
	private LocalDateTime completedAt;

	public enum ExecutionStatus {
		INITIATED, ORDER_CREATED, PAYMENT_PENDING, PAYMENT_AUTHORIZED, PAYMENT_CAPTURED, SUCCESS, FAILED, RETRY
	}
}
