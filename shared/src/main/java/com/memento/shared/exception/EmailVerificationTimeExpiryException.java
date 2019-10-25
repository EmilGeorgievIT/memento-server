package com.memento.shared.exception;

public class EmailVerificationTimeExpiryException extends RuntimeException {

    private static final long serialVersionUID = 9016765657097092361L;

    public EmailVerificationTimeExpiryException() {
        super();
    }

    public EmailVerificationTimeExpiryException(String message) {
        super(message);
    }

    public EmailVerificationTimeExpiryException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailVerificationTimeExpiryException(Throwable cause) {
        super(cause);
    }

    protected EmailVerificationTimeExpiryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
