package com.vegesoft.moneytracker.account.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountTest {

    @Test
    @DisplayName("Should add balance")
    void shouldAdd_balance() {
        //Given
        final BigDecimal amount = BigDecimal.TEN;
        final Balance toAdd = new Balance(amount);
        final Account account = createInitialAccount();
        //When
        account.addBalance(toAdd);
        //Then
        assertEquals(amount, account.getBalance().getAmount());
    }

    @Test
    @DisplayName("Should subtract balance")
    void shouldSubtract_balance() {
        //Given
        final BigDecimal amount = BigDecimal.TEN;
        final Balance toSubtract = new Balance(amount);
        final Account account = createInitialAccount();
        //When
        account.subtractBalance(toSubtract);
        //Then
        assertEquals(amount.negate(), account.getBalance().getAmount());
    }

    @Test
    @DisplayName("Should throw exception when add negative balance")
    void shouldNegativeAdd_throwException() {
        //Given
        final BigDecimal amount = new BigDecimal("-10");
        final Balance toAdd = new Balance(amount);
        final Account account = createInitialAccount();
        //When
        Assertions.assertThrows(DomainLogicException.class, () -> account.addBalance(toAdd));
        //Then was thrown
    }

    @Test
    @DisplayName("Should throw exception when subtract negative balance")
    void shouldNegativeSubtract_throwException() {
        //Given
        final BigDecimal amount = new BigDecimal("-10");
        final Balance toAdd = new Balance(amount);
        final Account account = createInitialAccount();
        //When
        Assertions.assertThrows(DomainLogicException.class, () -> account.subtractBalance(toAdd));
        //Then was thrown
    }

    private Account createInitialAccount() {
        return new Account(UUID.randomUUID(), new Balance(BigDecimal.ZERO));
    }
}