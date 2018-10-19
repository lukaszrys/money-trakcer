package com.vegesoft.moneytracker.accounthistory.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public final class Income {

    @Id
    private final UUID id;
    @NotNull
    private final Amount amount;
    @NotNull
    private final LocalDateTime createdAt;
    @NotNull
    private final UUID accountId;

    public Income(final UUID id, @NotNull final Amount amount, @NotNull final LocalDateTime createdAt,
        @NotNull final UUID accountId) {
        this.id = id;
        this.amount = amount;
        this.createdAt = createdAt;
        this.accountId = accountId;
    }

    public UUID getId() {
        return id;
    }

    public Amount getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getAccountId() {
        return accountId;
    }
}
