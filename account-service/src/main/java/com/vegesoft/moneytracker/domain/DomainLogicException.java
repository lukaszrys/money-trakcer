package com.vegesoft.moneytracker.domain;

public class DomainLogicException extends RuntimeException {

    public DomainLogicException(final String message) {
        super(message);
    }

    public DomainLogicException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
