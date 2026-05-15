package com.dps.profile_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dps.profile_service.dto.BankAccountResponse;
import com.dps.profile_service.dto.CreateProfileRequest;
import com.dps.profile_service.dto.ProfileResponse;
import com.dps.profile_service.dto.UpdateProfileRequest;
import com.dps.profile_service.entity.SellerProfile;
import com.dps.profile_service.service.ProfileService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    
    @Autowired
    private ProfileService profileService;
    
    @PostMapping
    public ProfileResponse createProfile(
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Role") String role,
            @Valid @RequestBody CreateProfileRequest request) {
        return profileService.createProfile(userId, request);
    }
    
    @GetMapping("/me")
    public ProfileResponse getProfile(
            @RequestHeader("X-User-Id") String userId) {
 return profileService.getProfile(userId);
    }
    
    @PutMapping("/me")
    public ProfileResponse updateProfile(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody UpdateProfileRequest request) {
        
        return profileService.updateProfile(userId, request);
    }
    
    // Internal endpoint for payment executor (get bank account)
    @GetMapping("/{sellerId}/bank-account")
    public BankAccountResponse getBankAccount(
            @PathVariable String sellerId,
            @RequestHeader(value = "X-Gateway-Secret", required = false) String secret) {

        
        return profileService.getBankAccount(sellerId);
    }
    
    // Admin endpoint
    @PatchMapping("/{sellerId}/kyc")
    public void updateKycStatus(
            @PathVariable String sellerId,
            @RequestHeader("X-User-Role") String role,
            @RequestBody SellerProfile.KycStatus request) {
        
    	profileService.updateKycStatus(sellerId, request);
    }
    
    
}