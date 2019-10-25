package com.memento.shared.exception;

public class MementoException extends RuntimeException {

    private static final long serialVersionUID = 6519646150616931907L;

    public MementoException() {
        super();
    }

    public MementoException(String message) {
        super(message);
    }

    public MementoException(String message, Throwable cause) {
        super(message, cause);
    }

    public MementoException(Throwable cause) {
        super(cause);
    }

    protected MementoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
