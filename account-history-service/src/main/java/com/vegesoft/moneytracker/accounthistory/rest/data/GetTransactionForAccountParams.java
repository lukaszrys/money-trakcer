package com.vegesoft.moneytracker.accounthistory.rest.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.format.annotation.DateTimeFormat;

public class GetTransactionForAccountParams {

    private final LocalDateTime from;
    private final LocalDateTime to;

    @JsonCreator
    public GetTransactionForAccountParams(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @JsonProperty("from") final LocalDateTime from,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @JsonProperty("to") final LocalDateTime to) {
        this.from = from == null ? LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.MIN) : from;
        this.to = to == null ? LocalDateTime.now() : to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }
}
