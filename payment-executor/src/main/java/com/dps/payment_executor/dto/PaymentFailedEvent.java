package com.dps.payment_executor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentFailedEvent {
	private String paymentOrderId;
	private String sellerId;
	private String buyerId;
	private BigDecimal amount;
	private String currency;
	private String failureReason;
	private Long timestamp;
}
