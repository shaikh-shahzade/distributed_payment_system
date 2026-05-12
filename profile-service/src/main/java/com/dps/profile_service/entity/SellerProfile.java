package com.dps.profile_service.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seller_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerProfile implements Serializable {

 @Id
 @Column(name = "user_id", length = 36)
 private String userId; 

 @Column(name = "business_name", nullable = false, length = 100)
 private String businessName;

 @Column(name = "bank_account", nullable = false, length = 50)
 private String bankAccount;

 @Column(name = "bank_name", nullable = false, length = 100)
 private String bankName;

 @Column(name = "ifsc_code", nullable = false, length = 20)
 private String ifscCode;

 @Enumerated(EnumType.STRING)
 @Column(name = "kyc_status", nullable = false)
 private KycStatus kycStatus = KycStatus.PENDING;

 @Column(name = "preferred_currency", length = 3)
 private String preferredCurrency = "INR";

 @Column(name = "contact_phone", length = 15)
 private String contactPhone;

 @Column(name = "created_at", updatable = false)
 @CreationTimestamp
 private LocalDateTime createdAt;

 @Column(name = "updated_at")
 @UpdateTimestamp
 private LocalDateTime updatedAt;

 public enum KycStatus {
     PENDING, VERIFIED, REJECTED
 }
}