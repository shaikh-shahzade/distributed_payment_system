package com.dps.notification_service.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Value("${notification.email.from-name:DPPS Platform}")
	private String fromName;

	public void sendEmail(String referenceId, String notificationType, String toEmail, String subject,
			String htmlBody) {

		log.info("EMAIL_ATTEMPT | type: {} | to: {} | ref: {}", notificationType, toEmail, referenceId);

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setFrom(fromEmail, fromName);
			helper.setTo(toEmail);
			helper.setSubject(subject);
			helper.setText(htmlBody, true); // true = HTML

			mailSender.send(message);

			log.info("EMAIL_SENT | type: {} | to: {} | ref: {}", notificationType, toEmail, referenceId);

		} catch (Exception e) {
			log.error("EMAIL_FAILED | type: {} | to: {} | ref: {} | error: {}", notificationType, toEmail, referenceId,
					e.getMessage(), e);
		}
	}
}
