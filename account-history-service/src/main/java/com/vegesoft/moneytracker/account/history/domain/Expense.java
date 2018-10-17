package com.vegesoft.moneytracker.account.history.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public final class Expense {

    @Id
    private final UUID id;
    @NotNull
    private final Amount amount;
    @NotNull
    private final String type;
    @NotNull
    private final LocalDateTime createdAt;
    @NotNull
    private final UUID accountId;

    public Expense(final UUID id, @NotNull final Amount amount, @NotNull final String type,
        @NotNull final LocalDateTime createdAt, final @NotNull UUID accountId) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
        this.accountId = accountId;
    }

    public UUID getId() {
        return id;
    }

    public Amount getAmount() {
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
