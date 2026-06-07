package com.dps.paymentserver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "payment-executor", path = "/api/v1/executor")
public interface PaymentExecutorClient {

	@PostMapping("/payment")
	PaymentExecutionResponse executePayment(@RequestBody PaymentExecutionRequest request);

	@PostMapping("/transfer/execute")
	RouteTransferResponse executeTransfer(@RequestBody RouteTransferRequest request);

	@GetMapping("/payment/{paymentOrderId}")
	PaymentExecutionResponse getPaymentStatus(@PathVariable String paymentOrderId);

	record PaymentExecutionRequest(String paymentOrderId, String checkoutId, String sellerId, BigDecimal amount,
			String currency, String buyerEmail, String buyerPhone, String description) {
	}

	record PaymentExecutionResponse(String paymentOrderId, String status, String razorpayOrderId,
			String razorpayPaymentId, String failureReason) {
	}

	record RouteTransferRequest(String transferId, String paymentOrderId, String checkoutId, String sellerId,
			String razorpayPaymentId, String razorpayLinkedAccountId, BigDecimal totalAmount, String currency,
			BigDecimal commissionPercent) {
	}

	record RouteTransferResponse(String transferId, String paymentOrderId, String sellerId, String razorpayTransferId,
			BigDecimal totalAmount, BigDecimal sellerAmount, BigDecimal platformAmount, BigDecimal commissionPercent,
			String currency, String status) {
	}
}
