package com.dps.payment_executor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "route_transfer_executions", indexes = { @Index(name = "idx_transfer_id", columnList = "transfer_id"),
		@Index(name = "idx_rte_payment_order_id", columnList = "payment_order_id"),
		@Index(name = "idx_rte_seller_id", columnList = "seller_id"),
		@Index(name = "idx_razorpay_payment_id", columnList = "razorpay_payment_id"),
		@Index(name = "idx_razorpay_transfer_id", columnList = "razorpay_transfer_id"),
		@Index(name = "idx_rte_status", columnList = "status") })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteTransferExecution {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "transfer_id", unique = true, nullable = false, length = 100)
	private String transferId;

	@Column(name = "payment_order_id", nullable = false, length = 100)
	private String paymentOrderId;

	@Column(name = "checkout_id", nullable = false, length = 100)
	private String checkoutId;

	@Column(name = "seller_id", nullable = false, length = 100)
	private String sellerId;

	// The Razorpay payment_id of the captured payment to split
	@Column(name = "razorpay_payment_id", nullable = false, length = 100)
	private String razorpayPaymentId;

	// Seller's Razorpay linked account ID (acc_XXXXX)
	@Column(name = "razorpay_linked_account_id", nullable = false, length = 100)
	private String razorpayLinkedAccountId;

	@Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
	private BigDecimal totalAmount;

	@Column(name = "seller_amount", nullable = false, precision = 19, scale = 2)
	private BigDecimal sellerAmount;

	@Column(name = "platform_amount", nullable = false, precision = 19, scale = 2)
	private BigDecimal platformAmount;

	@Column(name = "commission_percent", nullable = false, precision = 5, scale = 2)
	private BigDecimal commissionPercent;

	@Column(name = "currency", nullable = false, length = 3)
	private String currency;

	// Razorpay transfer ID returned after creating the route transfer
	@Column(name = "razorpay_transfer_id", length = 100)
	private String razorpayTransferId;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private TransferStatus status;

	@Column(name = "failure_reason", length = 1000)
	private String failureReason;

	// If true, money is held in platform account until explicitly released
	@Column(name = "on_hold", nullable = false)
	@Builder.Default
	private boolean onHold = false;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "completed_at")
	private LocalDateTime completedAt;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public enum TransferStatus {
		INITIATED, TRANSFER_CREATED, PROCESSING, SUCCESS, FAILED
	}
}
