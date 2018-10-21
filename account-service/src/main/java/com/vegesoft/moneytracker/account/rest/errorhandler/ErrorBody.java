package com.vegesoft.moneytracker.account.rest.errorhandler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ErrorBody {

    private final String message;
    private final int status;

    @JsonCreator
    public ErrorBody(@JsonProperty("message") final String message, @JsonProperty("status") final int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
