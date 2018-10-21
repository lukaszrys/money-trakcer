package com.vegesoft.moneytracker.account.query.view;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.UUID;

public final class AccountView {

    private final UUID accountId;
    private final BigDecimal amount;

    @JsonCreator
    public AccountView(@JsonProperty("accountId") final UUID accountId,
        @JsonProperty("amount") final BigDecimal amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
