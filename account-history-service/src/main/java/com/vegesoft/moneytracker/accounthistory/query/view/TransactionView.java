package com.vegesoft.moneytracker.accounthistory.query.view;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionView {

    private final BigDecimal amount;
    private final String type;
    private final LocalDateTime createdAt;
    private final UUID accountId;

    @JsonCreator
    public TransactionView(@JsonProperty("amount") final BigDecimal amount, @JsonProperty("type") final String type,
        @JsonProperty("createdAt") final LocalDateTime createdAt, @JsonProperty("accountId") final UUID accountId) {
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getAccountId() {
        return accountId;
    }
}
