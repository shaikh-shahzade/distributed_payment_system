package com.dps.payment_executor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentExecutionResponse {

	private String paymentOrderId;
	private String razorpayOrderId;
	private String status;
	private BigDecimal amount;
	private String currency;
	private LocalDateTime createdAt;
}
