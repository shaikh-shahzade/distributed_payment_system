package com.dps.profile_service.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
