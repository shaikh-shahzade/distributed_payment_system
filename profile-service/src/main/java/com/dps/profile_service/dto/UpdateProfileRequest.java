package com.dps.profile_service.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProfileRequest {

    @Size(max = 100)
    private String businessName;

    private String bankAccount;

    private String bankName;

    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$",
             message = "Invalid IFSC code format")
    private String ifscCode;

    @Pattern(regexp = "^[6-9]\\d{9}$",
             message = "Invalid Indian phone number")
    private String contactPhone;

    private String preferredCurrency;
}