package com.dps.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycUpdatedEvent {
	private String sellerId;
	private String sellerEmail;
	private String sellerName;
	private String kycStatus;
	private String remarks;
	private LocalDateTime timestamp;
}
