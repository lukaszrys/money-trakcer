package com.vegesoft.moneytracker.accounthistory.handler;

import com.vegesoft.moneytracker.accounthistory.command.AddExpenseCommand;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface ExpenseCommandHandler {

    Mono<Void> handle(final UUID uuid, final AddExpenseCommand addExpenseCommand);
}
