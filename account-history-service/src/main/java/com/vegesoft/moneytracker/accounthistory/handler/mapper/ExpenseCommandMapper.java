package com.vegesoft.moneytracker.accounthistory.handler.mapper;

import com.vegesoft.moneytracker.accounthistory.command.AddExpenseCommand;
import com.vegesoft.moneytracker.accounthistory.domain.Amount;
import com.vegesoft.moneytracker.accounthistory.domain.Expense;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ExpenseCommandMapper {

    public Expense map(final UUID id, final AddExpenseCommand command) {
        return new Expense(id, new Amount(command.getAmount()), command.getType(), command.getCreatedAt(),
            command.getAccountId());
    }
}
