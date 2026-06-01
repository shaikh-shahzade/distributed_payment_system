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
public class TransferCompletedEvent {

	private String transferId;
	private String paymentOrderId;
	private String sellerId;
	private BigDecimal totalAmount;
	private BigDecimal sellerAmount;
	private BigDecimal platformAmount;
	private BigDecimal commissionPercent;
	private String razorpayTransferId;
	private String razorpayPaymentId;
	private String currency;
	private LocalDateTime timestamp;
}
