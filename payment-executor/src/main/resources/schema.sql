-- ============================================================================
-- DPPS - Payment Executor Service Database Schema
-- Database: executor_db
-- ============================================================================

CREATE DATABASE IF NOT EXISTS executor_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE executor_db;

-- ============================================================================
-- Table: payment_executions
-- ============================================================================

CREATE TABLE payment_executions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_order_id VARCHAR(100) NOT NULL UNIQUE,
    checkout_id VARCHAR(100) NOT NULL,
    seller_id VARCHAR(100) NOT NULL,
    buyer_id VARCHAR(100) NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    currency VARCHAR(3) NOT NULL DEFAULT 'INR',
    razorpay_order_id VARCHAR(100),
    razorpay_payment_id VARCHAR(100),
    razorpay_signature VARCHAR(500),
    status VARCHAR(20) NOT NULL DEFAULT 'INITIATED',
    retry_count INT NOT NULL DEFAULT 0,
    failure_reason VARCHAR(1000),
    webhook_received BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    completed_at TIMESTAMP,
    
    INDEX idx_payment_order_id (payment_order_id),
    INDEX idx_razorpay_order_id (razorpay_order_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- Table: route_transfer_executions
-- ============================================================================

CREATE TABLE route_transfer_executions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transfer_id VARCHAR(100) NOT NULL UNIQUE,
    payment_order_id VARCHAR(100) NOT NULL,
    checkout_id VARCHAR(100) NOT NULL,
    seller_id VARCHAR(100) NOT NULL,
    razorpay_payment_id VARCHAR(100) NOT NULL,
    razorpay_linked_account_id VARCHAR(100) NOT NULL,
    total_amount DECIMAL(19,2) NOT NULL,
    seller_amount DECIMAL(19,2) NOT NULL,
    platform_amount DECIMAL(19,2) NOT NULL,
    commission_percent DECIMAL(5,2) NOT NULL DEFAULT 5.00,
    currency VARCHAR(3) NOT NULL DEFAULT 'INR',
    razorpay_transfer_id VARCHAR(100),
    status VARCHAR(20) NOT NULL DEFAULT 'INITIATED',
    failure_reason VARCHAR(1000),
    on_hold BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    completed_at TIMESTAMP,

    INDEX idx_transfer_id (transfer_id),
    INDEX idx_rte_payment_order_id (payment_order_id),
    INDEX idx_rte_seller_id (seller_id),
    INDEX idx_razorpay_payment_id (razorpay_payment_id),
    INDEX idx_razorpay_transfer_id (razorpay_transfer_id),
    INDEX idx_rte_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- Table: webhook_events
-- ============================================================================

CREATE TABLE webhook_events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id VARCHAR(100) NOT NULL UNIQUE,
    entity_id VARCHAR(100) NOT NULL,
    event_type VARCHAR(50) NOT NULL,
    payload TEXT,
    processed BOOLEAN NOT NULL DEFAULT FALSE,
    failure_reason VARCHAR(1000),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processed_at TIMESTAMP,
    
    INDEX idx_event_id (event_id),
    INDEX idx_entity_id (entity_id),
    INDEX idx_event_type (event_type),
    INDEX idx_processed (processed)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
