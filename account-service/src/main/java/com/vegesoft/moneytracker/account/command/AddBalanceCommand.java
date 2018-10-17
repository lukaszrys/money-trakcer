package com.vegesoft.moneytracker.account.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddBalanceCommand {

    @NotNull
    @Min(0)
    private final BigDecimal amount;

    @JsonCreator
    public AddBalanceCommand(@JsonProperty("amount") final BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
