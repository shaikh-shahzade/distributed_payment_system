package com.dps.notification_service.consumer;

import com.dps.notification_service.dto.PayoutCompletedEvent;
import com.dps.notification_service.dto.PayoutFailedEvent;
import com.dps.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PayoutNotificationConsumer {

	private final NotificationService notificationService;

	@KafkaListener(topics = "payout.completed", groupId = "notification-service-group", containerFactory = "kafkaListenerContainerFactory")
	public void consumePayoutCompleted(PayoutCompletedEvent event, Acknowledgment ack) {
		log.info("Received payout.completed | payoutId: {}", event.getPayoutOrderId());
		try {
			notificationService.sendPayoutCompleted(event);
		} catch (Exception e) {
			log.error("Failed to process payout.completed notification | payoutId: {} | error: {}",
					event.getPayoutOrderId(), e.getMessage(), e);
		} finally {
			ack.acknowledge();
		}
	}

	@KafkaListener(topics = "payout.failed", groupId = "notification-service-group", containerFactory = "kafkaListenerContainerFactory")
	public void consumePayoutFailed(PayoutFailedEvent event, Acknowledgment ack) {
		log.info("Received payout.failed | payoutId: {}", event.getPayoutOrderId());
		try {
			notificationService.sendPayoutFailed(event);
		} catch (Exception e) {
			log.error("Failed to process payout.failed notification | payoutId: {} | error: {}",
					event.getPayoutOrderId(), e.getMessage(), e);
		} finally {
			ack.acknowledge();
		}
	}
}
