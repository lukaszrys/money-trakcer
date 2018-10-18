package com.vegesoft.moneytracker.account.handler.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.vegesoft.moneytracker.account.command.CreateAccountCommand;
import com.vegesoft.moneytracker.account.domain.Account;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountCommandMapperTest {

    private final AccountCommandMapper accountCommandMapper = new AccountCommandMapper();

    @Test
    @DisplayName("Should map create account command to account")
    void mapToAccount() {
        //Given
        final UUID id = UUID.randomUUID();
        final BigDecimal initialValue = BigDecimal.TEN;
        final CreateAccountCommand command = new CreateAccountCommand(initialValue);
        //When
        final Account account = accountCommandMapper.mapToAccount(id, command);
        //Then
        assertEquals(id, account.getId());
        assertEquals(initialValue, account.getBalance().getAmount());
    }
}