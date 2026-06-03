package com.dps.payment_executor.controller;

import com.dps.payment_executor.dto.ExecutePaymentRequest;
import com.dps.payment_executor.dto.PaymentExecutionResponse;
import com.dps.payment_executor.service.PaymentExecutorService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/executor/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentExecutorController {

	private final PaymentExecutorService paymentExecutorService;
	private final MeterRegistry meterRegistry;

	@PostMapping("/execute")
	public ResponseEntity<PaymentExecutionResponse> executePayment(@Valid @RequestBody ExecutePaymentRequest request) {

		log.info("Received payment execution request for order: {}", request.getPaymentOrderId());

		PaymentExecutionResponse response = paymentExecutorService.executePayment(request);

		Counter.builder("dpps.payment.execution.initiated").tag("seller", request.getSellerId()).register(meterRegistry)
				.increment();

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{paymentOrderId}")
	public ResponseEntity<PaymentExecutionResponse> getPaymentExecution(@PathVariable String paymentOrderId) {

		log.info("Fetching payment execution for order: {}", paymentOrderId);

		PaymentExecutionResponse response = paymentExecutorService.getPaymentExecution(paymentOrderId);
		return ResponseEntity.ok(response);
	}
}
