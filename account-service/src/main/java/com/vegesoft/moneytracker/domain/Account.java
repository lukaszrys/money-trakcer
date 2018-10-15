package com.vegesoft.moneytracker.domain;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public final class Account {

    @Id
    private final UUID id;
    private Balance balance;
    private AccountStatus status;

    public Account(final UUID id, final Balance balance) {
        this.id = id;
        this.balance = balance;
        this.status = AccountStatus.INACTIVE;
    }

    public UUID getId() {
        return id;
    }

    public Balance getBalance() {
        return balance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void addBalance(final Balance balance) {
        if (balance.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainLogicException("Added amount cannot be less than zero");
        }
        this.balance = new Balance(this.balance.getAmount().add(balance.getAmount()));
    }

    public void substractBalance(final Balance balance) {
        if (balance.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainLogicException("Subtracted amount cannot be less than zero");
        }
        this.balance = new Balance(this.balance.getAmount().subtract(balance.getAmount()));
    }
}
