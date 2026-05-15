package com.dps.profile_service.serviceimpl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dps.profile_service.dto.BankAccountResponse;
import com.dps.profile_service.dto.CreateProfileRequest;
import com.dps.profile_service.dto.ProfileResponse;
import com.dps.profile_service.dto.UpdateProfileRequest;
import com.dps.profile_service.entity.SellerProfile;
import com.dps.profile_service.exception.ProfileAlreadyExistsException;
import com.dps.profile_service.exception.ProfileNotFoundException;
import com.dps.profile_service.repository.ProfileRepository;
import com.dps.profile_service.service.ProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    // READ — checks Redis first, falls back to MySQL
    @Override
    @Cacheable(value = "profile", key = "#userId")
    public ProfileResponse getProfile(String userId) {
        log.info("Cache miss — fetching from MySQL for userId: {}", userId);
        SellerProfile profile = profileRepository
                .findById(userId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "Profile not found for userId: " + userId));
        return mapToProfileResponse(profile);
    }

    // CREATE — writes to MySQL then populates Redis
    @Override
    @CachePut(value = "profile", key = "#userId")
    public ProfileResponse createProfile(String userId,
                                         CreateProfileRequest request) {
        if (profileRepository.existsById(userId)) {
            throw new ProfileAlreadyExistsException(
                    "Profile already exists for userId: " + userId);
        }

        SellerProfile profile = SellerProfile.builder()
                .userId(userId)
                .businessName(request.getBusinessName())
                .bankAccount(request.getBankAccount())
                .bankName(request.getBankName())
                .ifscCode(request.getIfscCode())
                .contactPhone(request.getContactPhone())
                .preferredCurrency(request.getPreferredCurrency())
                .kycStatus(SellerProfile.KycStatus.PENDING)
                .build();

        SellerProfile saved = profileRepository.save(profile);
        log.info("Profile created for userId: {}", userId);
        return mapToProfileResponse(saved);
    }

    // UPDATE — writes MySQL then evicts Redis key
    @Override
    @CacheEvict(value = "profile", key = "#userId")
    public ProfileResponse updateProfile(String userId,
                                         UpdateProfileRequest request) {
        SellerProfile profile = profileRepository
                .findById(userId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "Profile not found for userId: " + userId));

        // Only update fields that are not null in the request
        if (request.getBusinessName() != null)
            profile.setBusinessName(request.getBusinessName());
        if (request.getBankAccount() != null)
            profile.setBankAccount(request.getBankAccount());
        if (request.getBankName() != null)
            profile.setBankName(request.getBankName());
        if (request.getIfscCode() != null)
            profile.setIfscCode(request.getIfscCode());
        if (request.getContactPhone() != null)
            profile.setContactPhone(request.getContactPhone());
        if (request.getPreferredCurrency() != null)
            profile.setPreferredCurrency(request.getPreferredCurrency());

        SellerProfile updated = profileRepository.save(profile);
        log.info("Profile updated for userId: {}", userId);
        return mapToProfileResponse(updated);
    }

    // Called by payment-executor — separate cache key for bank details
    @Override
    @Cacheable(value = "profile-bank", key = "#sellerId")
    public BankAccountResponse getBankAccount(String sellerId) {
        log.info("Fetching bank account for sellerId: {}", sellerId);
        SellerProfile profile = profileRepository
                .findById(sellerId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "Profile not found for sellerId: " + sellerId));
        return mapToBankAccountResponse(profile);
    }

    // Called by admin / KYC system
    @Override
    @CacheEvict(value = "profile", key = "#sellerId")
    public void updateKycStatus(String sellerId,
                                SellerProfile.KycStatus status) {
        SellerProfile profile = profileRepository
                .findById(sellerId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "Profile not found for sellerId: " + sellerId));
        profile.setKycStatus(status);
        profileRepository.save(profile);
        log.info("KYC status updated to {} for sellerId: {}", status, sellerId);
    }

    // Mappers
    private ProfileResponse mapToProfileResponse(SellerProfile p) {
        return ProfileResponse.builder()
                .userId(p.getUserId())
                .businessName(p.getBusinessName())
                .bankAccount(p.getBankAccount())
                .bankName(p.getBankName())
                .ifscCode(p.getIfscCode())
                .kycStatus(p.getKycStatus().name())
                .preferredCurrency(p.getPreferredCurrency())
                .contactPhone(p.getContactPhone())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }

    private BankAccountResponse mapToBankAccountResponse(SellerProfile p) {
        return BankAccountResponse.builder()
                .userId(p.getUserId())
                .bankAccount(p.getBankAccount())
                .bankName(p.getBankName())
                .ifscCode(p.getIfscCode())
                .preferredCurrency(p.getPreferredCurrency())
                .build();
    }
}
