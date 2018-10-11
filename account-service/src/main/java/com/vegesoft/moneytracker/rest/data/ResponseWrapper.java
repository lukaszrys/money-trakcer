package com.vegesoft.moneytracker.rest.data;

public class ResponseWrapper<T> {

    private final T data;

    public ResponseWrapper(final T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
