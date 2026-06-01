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
public class RouteTransferResponse {

	private String transferId;
	private String paymentOrderId;
	private String sellerId;
	private String razorpayTransferId;
	private BigDecimal totalAmount;
	private BigDecimal sellerAmount;
	private BigDecimal platformAmount;
	private BigDecimal commissionPercent;
	private String currency;
	private String status;
	private boolean onHold;
	private LocalDateTime createdAt;
}
