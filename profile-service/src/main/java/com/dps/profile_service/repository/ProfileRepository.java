package com.dps.profile_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dps.profile_service.entity.SellerProfile;

@Repository
public interface ProfileRepository
        extends JpaRepository<SellerProfile, String> {

    Optional<SellerProfile> findByUserId(String userId);
}