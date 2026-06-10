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
public class PayoutCompletedEvent {
	private String payoutOrderId;
	private String razorpayTransferId;
	private String sellerId;
	private String sellerEmail;
	private String sellerName;
	private BigDecimal amount;
	private BigDecimal commissionAmount;
	private String currency;
	private String utr;
	private LocalDateTime timestamp;
}
