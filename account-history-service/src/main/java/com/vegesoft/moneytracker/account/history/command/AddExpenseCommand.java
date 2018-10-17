package com.vegesoft.moneytracker.account.history.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddExpenseCommand {

    @NotNull
    @Min(0)
    private final BigDecimal amount;
    @NotNull
    private final String type;
    @NotNull
    private final UUID accountId;
    @NotNull
    private final LocalDateTime createdAt;

    @JsonCreator
    public AddExpenseCommand(@JsonProperty @NotNull @Min(0) final BigDecimal amount,
        @JsonProperty @NotNull final String type, @JsonProperty @NotNull final UUID accountId,
        @JsonProperty @NotNull final LocalDateTime createdAt) {
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
