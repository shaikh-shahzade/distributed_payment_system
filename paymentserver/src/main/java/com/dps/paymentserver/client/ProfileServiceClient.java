package com.dps.paymentserver.client;

import com.dps.paymentserver.dto.BankAccountResponse;
import com.dps.paymentserver.dto.ProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile-service", path = "/api/v1/profile")
public interface ProfileServiceClient {

	@GetMapping("/{sellerId}/bank-account")
	BankAccountResponse getBankAccount(@PathVariable String sellerId);

	@GetMapping("/sellers/{sellerId}")
	ProfileResponse getSellerProfile(@PathVariable String sellerId);
}
