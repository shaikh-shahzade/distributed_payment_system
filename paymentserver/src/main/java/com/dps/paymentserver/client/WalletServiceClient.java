package com.dps.paymentserver.client;

import com.dps.paymentserver.dto.WalletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "wallet-service", path = "/api/v1/wallet")
public interface WalletServiceClient {

	@GetMapping("/{sellerId}")
	WalletResponse getWallet(@PathVariable String sellerId);

	@GetMapping("/{sellerId}/balance")
	WalletBalanceResponse getBalance(@PathVariable String sellerId);

	record WalletBalanceResponse(String sellerId, java.math.BigDecimal balance, String currency) {
	}
}
