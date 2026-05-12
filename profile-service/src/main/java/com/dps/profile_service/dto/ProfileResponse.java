package com.dps.profile_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {

    private String userId;
    private String businessName;
    private String bankAccount;
    private String bankName;
    private String ifscCode;
    private String kycStatus;
    private String preferredCurrency;
    private String contactPhone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}