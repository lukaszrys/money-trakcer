package com.vegesoft.moneytracker.account.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public final class SubtractBalanceCommand {

    @NotNull
    @Min(0)
    private final BigDecimal amount;
    @NotNull
    private final String expenseType;

    @JsonCreator
    public SubtractBalanceCommand(@JsonProperty("amount") final BigDecimal amount, @JsonProperty("expenseType") final String expenseType) {
        this.amount = amount;
        this.expenseType = expenseType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getExpenseType() {
        return expenseType;
    }
}
