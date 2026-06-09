package com.dps.notification_service.consumer;

import com.dps.notification_service.dto.KycUpdatedEvent;
import com.dps.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KycNotificationConsumer {

	private final NotificationService notificationService;

	@KafkaListener(topics = "kyc.updated", groupId = "notification-service-group", containerFactory = "kafkaListenerContainerFactory")
	public void consumeKycUpdated(KycUpdatedEvent event, Acknowledgment ack) {
		log.info("Received kyc.updated | sellerId: {} | status: {}", event.getSellerId(), event.getKycStatus());
		try {
			notificationService.sendKycUpdated(event);
		} catch (Exception e) {
			log.error("Failed to process kyc.updated notification | sellerId: {} | error: {}", event.getSellerId(),
					e.getMessage(), e);
		} finally {
			ack.acknowledge();
		}
	}
}
