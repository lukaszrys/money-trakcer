package com.vegesoft.moneytracker.account.handler.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.vegesoft.moneytracker.account.client.data.ExpenseRequest;
import com.vegesoft.moneytracker.account.command.SubtractBalanceCommand;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BalanceCommandMapperTest {

    private final BalanceCommandMapper balanceCommandMapper = new BalanceCommandMapper();

    @Test
    @DisplayName("Should create expense request based on command")
    void expenseRequest() {
        //Given
        final UUID accountId = UUID.randomUUID();
        final BigDecimal amount = BigDecimal.TEN;
        final String expenseType = "type";
        final SubtractBalanceCommand command = new SubtractBalanceCommand(amount, expenseType);
        //When
        final ExpenseRequest expenseRequest = balanceCommandMapper.expenseRequest(accountId, command);
        //Then
        assertEquals(expenseType, expenseRequest.getType());
        assertEquals(amount, expenseRequest.getAmount());
        assertEquals(accountId, expenseRequest.getAccountId());
    }
}