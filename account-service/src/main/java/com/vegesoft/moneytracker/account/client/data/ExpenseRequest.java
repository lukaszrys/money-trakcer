package com.vegesoft.moneytracker.account.client.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.NotNull;

public final class ExpenseRequest {

    @NotNull
    private final BigDecimal amount;
    @NotNull
    private final String type;
    @NotNull
    private final UUID accountId;
    @NotNull
    private final LocalDateTime createdAt;

    public ExpenseRequest(@NotNull final BigDecimal amount, @NotNull final String type, @NotNull final UUID accountId,
        @NotNull final LocalDateTime createdAt) {
        this.amount = amount;
        this.type = type;
        this.accountId = accountId;
        this.createdAt = createdAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
