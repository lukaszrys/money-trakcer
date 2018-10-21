package com.vegesoft.moneytracker.account.exception;

public class NotFoundAccountException extends RuntimeException {

    public NotFoundAccountException(final String message) {
        super(message);
    }
}
