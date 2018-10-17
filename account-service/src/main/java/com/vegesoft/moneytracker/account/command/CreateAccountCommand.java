package com.vegesoft.moneytracker.account.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

public final class CreateAccountCommand {

    @NotNull
    private final BigDecimal initialValue;

    @JsonCreator
    public CreateAccountCommand(@JsonProperty("initialValue") @NotNull final BigDecimal initialValue) {
        this.initialValue = initialValue;
    }

    public BigDecimal getInitialValue() {
        return initialValue;
    }
}
