package com.dps.profile_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Used by payment-executor — only returns what it needs
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccountResponse {

    private String userId;
    private String bankAccount;
    private String bankName;
    private String ifscCode;
    private String preferredCurrency;
}
