CREATE TABLE
    seller_profile (
        user_id VARCHAR(36) NOT NULL,
        business_name VARCHAR(100) NOT NULL,
        bank_account VARCHAR(50) NOT NULL,
        bank_name VARCHAR(100) NOT NULL,
        ifsc_code VARCHAR(20) NOT NULL,
        kyc_status ENUM ('PENDING', 'VERIFIED', 'REJECTED') NOT NULL DEFAULT 'PENDING',
        preferred_currency VARCHAR(3) NOT NULL DEFAULT 'INR',
        contact_phone VARCHAR(15) NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        CONSTRAINT pk_seller_profile PRIMARY KEY (user_id)
    );