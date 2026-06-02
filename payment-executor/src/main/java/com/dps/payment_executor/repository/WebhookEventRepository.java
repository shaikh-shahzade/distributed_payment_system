package com.dps.payment_executor.repository;

import com.dps.payment_executor.entity.WebhookEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebhookEventRepository extends JpaRepository<WebhookEvent, Long> {

    Optional<WebhookEvent> findByEventId(String eventId);
    
    List<WebhookEvent> findByProcessedFalse();
    
    List<WebhookEvent> findByEntityId(String entityId);
    
    boolean existsByEventId(String eventId);
}
