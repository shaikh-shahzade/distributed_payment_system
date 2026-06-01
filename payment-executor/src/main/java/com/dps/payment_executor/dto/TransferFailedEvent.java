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
public class TransferFailedEvent {

	private String transferId;
	private String paymentOrderId;
	private String sellerId;
	private BigDecimal totalAmount;
	private String failureReason;
	private String razorpayTransferId;
	private String currency;
	private LocalDateTime timestamp;
}
