package com.vegesoft.moneytracker.account.rest.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseWrapper<T> {

    private final T data;

    @JsonCreator
    public ResponseWrapper(@JsonProperty("data") final T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
