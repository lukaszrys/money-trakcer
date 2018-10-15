package com.vegesoft.moneytracker.domain;

import java.math.BigDecimal;

public final class Balance {

    private final BigDecimal amount;

    public Balance(final BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
