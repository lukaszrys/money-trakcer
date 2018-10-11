package com.vegesoft.moneytracker.command;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

public final class CreateAccountCommand {

    @NotNull
    private final BigDecimal initialValue;
    @NotNull
    private final String currency;

    public CreateAccountCommand(@NotNull final BigDecimal initialValue, final String currency) {
        this.initialValue = initialValue;
        this.currency = currency;
    }

    public BigDecimal getInitialValue() {
        return initialValue;
    }

    public String getCurrency() {
        return currency;
    }
}
