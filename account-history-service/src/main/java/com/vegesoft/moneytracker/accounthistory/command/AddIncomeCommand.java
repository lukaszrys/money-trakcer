package com.vegesoft.moneytracker.accounthistory.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public final class AddIncomeCommand {

    @NotNull
    @Min(0)
    private final BigDecimal amount;
    @NotNull
    private final UUID accountId;
    @NotNull
    private final LocalDateTime createdAt;

    @JsonCreator
    public AddIncomeCommand(@JsonProperty("amount") @NotNull @Min(0) final BigDecimal amount,
        @JsonProperty("accountId") @NotNull final UUID accountId,
        @JsonProperty("createdAt") @NotNull final LocalDateTime createdAt) {
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
