package com.dps.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFailedEvent {
	private String paymentOrderId;
	private String sellerId;
	private String sellerEmail;
	private String sellerName;
	private String buyerId;
	private String buyerEmail;
	private String buyerName;
	private BigDecimal amount;
	private String currency;
	private String failureReason;
	private LocalDateTime timestamp;
}
