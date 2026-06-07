package com.dps.paymentserver.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FeignClientConfig {

	@Bean
	public Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	@Bean
	public RequestInterceptor requestInterceptor() {
		return template -> {
			template.header("X-Service-Name", "payment-service");
			log.debug("Feign request: {} {}", template.method(), template.url());
		};
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return (methodKey, response) -> {
			log.error("Feign client error: {} - Status: {}", methodKey, response.status());
			return new ErrorDecoder.Default().decode(methodKey, response);
		};
	}
}
