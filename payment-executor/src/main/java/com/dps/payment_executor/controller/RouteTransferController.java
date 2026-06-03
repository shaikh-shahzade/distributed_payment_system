package com.dps.payment_executor.controller;

import com.dps.payment_executor.dto.ExecuteTransferRequest;
import com.dps.payment_executor.dto.RouteTransferResponse;
import com.dps.payment_executor.service.RouteTransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/executor/transfer")
@RequiredArgsConstructor
@Slf4j
public class RouteTransferController {

	private final RouteTransferService routeTransferService;

	@PostMapping("/execute")
	public ResponseEntity<RouteTransferResponse> executeTransfer(@Valid @RequestBody ExecuteTransferRequest request) {
		log.info("Route transfer request: transferId={}, paymentOrderId={}", request.getTransferId(),
				request.getPaymentOrderId());
		RouteTransferResponse response = routeTransferService.executeTransfer(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{transferId}")
	public ResponseEntity<RouteTransferResponse> getTransfer(@PathVariable String transferId) {
		return ResponseEntity.ok(routeTransferService.getTransfer(transferId));
	}
}
