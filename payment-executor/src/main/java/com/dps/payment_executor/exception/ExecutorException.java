package com.dps.payment_executor.exception;

import org.springframework.http.HttpStatus;

public class ExecutorException extends RuntimeException {

    private final HttpStatus status;

    public ExecutorException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public ExecutorException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ExecutorException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public ExecutorException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
