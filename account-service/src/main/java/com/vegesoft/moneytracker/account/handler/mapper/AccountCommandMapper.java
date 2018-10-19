package com.vegesoft.moneytracker.account.handler.mapper;

import com.vegesoft.moneytracker.account.command.CreateAccountCommand;
import com.vegesoft.moneytracker.account.domain.Account;
import com.vegesoft.moneytracker.account.domain.Balance;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class AccountCommandMapper {

    public Account mapToAccount(final UUID uuid, final CreateAccountCommand command) {
        return new Account(uuid, new Balance(command.getInitialValue()));
    }
}
