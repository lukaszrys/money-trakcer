package com.vegesoft.moneytracker.domain;

import java.math.BigDecimal;

public final class Balance {

    private final BigDecimal amount;
    private final String currency;

    public Balance(final BigDecimal amount, final String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
