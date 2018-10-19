package com.vegesoft.moneytracker.accounthistory.handler.mapper;

import com.vegesoft.moneytracker.accounthistory.command.AddIncomeCommand;
import com.vegesoft.moneytracker.accounthistory.domain.Amount;
import com.vegesoft.moneytracker.accounthistory.domain.Income;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class IncomeCommandMapper {

    public Income map(final UUID id, final AddIncomeCommand command) {
        return new Income(id, new Amount(command.getAmount()), command.getCreatedAt(), command.getAccountId());
    }
}
