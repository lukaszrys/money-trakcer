package com.vegesoft.moneytracker.domain;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public final class Account {

    @Id
    private final UUID id;
    private final Balance balance;
    private final AccountStatus status;

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
}
