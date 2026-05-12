package com.dps.profile_service.service;

import com.dps.profile_service.dto.BankAccountResponse;
import com.dps.profile_service.dto.CreateProfileRequest;
import com.dps.profile_service.dto.ProfileResponse;
import com.dps.profile_service.dto.UpdateProfileRequest;
import com.dps.profile_service.entity.SellerProfile;

public interface ProfileService {

    ProfileResponse createProfile(String userId,
            CreateProfileRequest request);

    ProfileResponse getProfile(String userId);

    ProfileResponse updateProfile(String userId,
            UpdateProfileRequest request);

    BankAccountResponse getBankAccount(String sellerId);

    void updateKycStatus(String sellerId,
            SellerProfile.KycStatus status);
}