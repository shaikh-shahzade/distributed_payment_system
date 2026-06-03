package com.dps.payment_executor.controller;

import com.dps.payment_executor.service.WebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/webhook")
@RequiredArgsConstructor
@Slf4j
public class WebhookController {

	private final WebhookService webhookService;

	@PostMapping("/razorpay")
	public ResponseEntity<Void> handleRazorpayWebhook(@RequestBody String payload,
			@RequestHeader("X-Razorpay-Signature") String signature) {

		log.info("Received Razorpay webhook");

		webhookService.handleWebhook(payload, signature);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/health")
	public ResponseEntity<HealthResponse> health() {
		return ResponseEntity.ok(new HealthResponse("UP", "Payment Executor Service is running"));
	}

	record HealthResponse(String status, String message) {
	}
}
