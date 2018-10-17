package com.vegesoft.moneytracker.account.history.handler.mapper;

import com.vegesoft.moneytracker.account.history.command.AddExpenseCommand;
import com.vegesoft.moneytracker.account.history.domain.Amount;
import com.vegesoft.moneytracker.account.history.domain.Expense;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ExpenseCommandMapper {

    public Expense map(final UUID id, final AddExpenseCommand command) {
        return new Expense(id, new Amount(command.getAmount()), command.getType(), command.getCreatedAt(),
            command.getAccountId());
    }
}
