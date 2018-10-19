package com.vegesoft.moneytracker.account.client.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.NotNull;

public final class IncomeRequest {

    @NotNull
    private final BigDecimal amount;
    @NotNull
    private final UUID accountId;
    @NotNull
    private final LocalDateTime createdAt;

    public IncomeRequest(@NotNull final BigDecimal amount, @NotNull final UUID accountId,
        @NotNull final LocalDateTime createdAt) {
        this.amount = amount;
        this.accountId = accountId;
        this.createdAt = createdAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
