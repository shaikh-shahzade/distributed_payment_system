package com.dps.payment_executor.kafka;

import com.dps.payment_executor.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	private static final String PAYMENT_APPROVED_TOPIC = "payment.approved";
	private static final String PAYMENT_FAILED_TOPIC = "payment.failed";
	private static final String PAYMENT_RETRY_TOPIC = "payment.retry";
	private static final String TRANSFER_COMPLETED_TOPIC = "transfer.completed";
	private static final String TRANSFER_FAILED_TOPIC = "transfer.failed";

	public void publishPaymentApproved(PaymentApprovedEvent event) {
		log.info("Publishing payment.approved for order: {}", event.getPaymentOrderId());
		publishEvent(PAYMENT_APPROVED_TOPIC, event.getPaymentOrderId(), event);
	}

	public void publishPaymentFailed(PaymentFailedEvent event) {
		log.info("Publishing payment.failed for order: {}", event.getPaymentOrderId());
		publishEvent(PAYMENT_FAILED_TOPIC, event.getPaymentOrderId(), event);
	}

	public void publishPaymentRetry(PaymentRetryEvent event) {
		log.info("Publishing payment.retry for order: {}", event.getPaymentOrderId());
		publishEvent(PAYMENT_RETRY_TOPIC, event.getPaymentOrderId(), event);
	}

	public void publishTransferCompleted(TransferCompletedEvent event) {
		log.info("Publishing transfer.completed for transfer: {}", event.getTransferId());
		publishEvent(TRANSFER_COMPLETED_TOPIC, event.getTransferId(), event);
	}

	public void publishTransferFailed(TransferFailedEvent event) {
		log.info("Publishing transfer.failed for transfer: {}", event.getTransferId());
		publishEvent(TRANSFER_FAILED_TOPIC, event.getTransferId(), event);
	}

	private void publishEvent(String topic, String key, Object event) {
		try {
			String message = objectMapper.writeValueAsString(event);

			CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, message);

			future.whenComplete((result, ex) -> {
				if (ex == null) {
					log.info("Published to topic={} key={} offset={}", topic, key, result.getRecordMetadata().offset());
				} else {
					log.error("Failed to publish to topic={} key={}: {}", topic, key, ex.getMessage(), ex);
				}
			});

		} catch (Exception e) {
			log.error("Error serializing event for topic={}: {}", topic, e.getMessage(), e);
			throw new RuntimeException("Failed to publish event to Kafka", e);
		}
	}
}
