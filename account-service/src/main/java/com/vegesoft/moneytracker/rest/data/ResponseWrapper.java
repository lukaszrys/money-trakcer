package com.vegesoft.moneytracker.rest.data;

public class ResponseWrapper<T> {

    private final T body;

    public ResponseWrapper(final T body) {
        this.body = body;
    }

    public T getData() {
        return body;
    }
}
