package com.vegesoft.moneytracker.account.history.handler;

import com.vegesoft.moneytracker.account.history.command.AddExpenseCommand;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface ExpenseCommandHandler {

    Mono<Void> handle(final UUID uuid, final AddExpenseCommand addExpenseCommand);
}
