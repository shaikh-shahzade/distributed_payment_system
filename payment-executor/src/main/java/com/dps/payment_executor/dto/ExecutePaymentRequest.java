package com.dps.payment_executor.dto;

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
public class ExecutePaymentRequest {

	@NotBlank(message = "Payment order ID is required")
	private String paymentOrderId;

	@NotBlank(message = "Checkout ID is required")
	private String checkoutId;

	@NotBlank(message = "Seller ID is required")
	private String sellerId;

	@NotBlank(message = "Buyer ID is required")
	private String buyerId;

	@NotNull(message = "Amount is required")
	@Positive(message = "Amount must be positive")
	private BigDecimal amount;

	@NotBlank(message = "Currency is required")
	private String currency;
}
