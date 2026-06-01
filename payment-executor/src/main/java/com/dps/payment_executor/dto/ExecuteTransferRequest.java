package com.dps.payment_executor.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecuteTransferRequest {

	@NotBlank(message = "Transfer ID is required")
	private String transferId;

	@NotBlank(message = "Payment order ID is required")
	private String paymentOrderId;

	@NotBlank(message = "Checkout ID is required")
	private String checkoutId;

	@NotBlank(message = "Seller ID is required")
	private String sellerId;

	@NotBlank(message = "Razorpay payment ID is required")
	private String razorpayPaymentId;

	@NotBlank(message = "Razorpay linked account ID is required")
	private String razorpayLinkedAccountId;

	@NotNull(message = "Total amount is required")
	@Positive(message = "Total amount must be positive")
	private BigDecimal totalAmount;

	@NotBlank(message = "Currency is required")
	private String currency;

	// Platform commission percentage — defaults to 5%
	@NotNull(message = "Commission percent is required")
	@DecimalMin(value = "0.0", message = "Commission cannot be negative")
	@DecimalMax(value = "100.0", message = "Commission cannot exceed 100%")
	@Builder.Default
	private BigDecimal commissionPercent = new BigDecimal("5.0");

	@Builder.Default
	private boolean onHold = false;
}
