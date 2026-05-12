package com.dps.profile_service.dto;

import jakarta.validation.constraints.NotBlank;
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
public class CreateProfileRequest {

    @NotBlank(message = "Business name is required")
    @Size(max = 100)
    private String businessName;

    @NotBlank(message = "Bank account is required")
    private String bankAccount;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @NotBlank(message = "IFSC code is required")
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$",
             message = "Invalid IFSC code format")
    private String ifscCode;

    @NotBlank(message = "Contact phone is required")
    @Pattern(regexp = "^[6-9]\\d{9}$",
             message = "Invalid Indian phone number")
    private String contactPhone;

    private String preferredCurrency = "INR";
}