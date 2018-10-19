package com.vegesoft.moneytracker.accounthistory.handler;

import com.vegesoft.moneytracker.accounthistory.command.AddIncomeCommand;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface IncomeCommandHandler {

    Mono<Void> handle(final UUID uuid, final AddIncomeCommand addIncomeCommand);
}
