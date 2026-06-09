package com.dps.notification_service.consumer;

import com.dps.notification_service.dto.PaymentApprovedEvent;
import com.dps.notification_service.dto.PaymentFailedEvent;
import com.dps.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentNotificationConsumer {

	private final NotificationService notificationService;

	@KafkaListener(topics = "payment.approved", groupId = "notification-service-group", containerFactory = "kafkaListenerContainerFactory")
	public void consumePaymentApproved(PaymentApprovedEvent event, Acknowledgment ack) {
		log.info("Received payment.approved | orderId: {}", event.getPaymentOrderId());
		try {
			notificationService.sendPaymentApproved(event);
		} catch (Exception e) {
			log.error("Failed to process payment.approved notification | orderId: {} | error: {}",
					event.getPaymentOrderId(), e.getMessage(), e);
		} finally {
			ack.acknowledge();
		}
	}

	@KafkaListener(topics = "payment.failed", groupId = "notification-service-group", containerFactory = "kafkaListenerContainerFactory")
	public void consumePaymentFailed(PaymentFailedEvent event, Acknowledgment ack) {
		log.info("Received payment.failed | orderId: {}", event.getPaymentOrderId());
		try {
			notificationService.sendPaymentFailed(event);
		} catch (Exception e) {
			log.error("Failed to process payment.failed notification | orderId: {} | error: {}",
					event.getPaymentOrderId(), e.getMessage(), e);
		} finally {
			ack.acknowledge();
		}
	}
}
