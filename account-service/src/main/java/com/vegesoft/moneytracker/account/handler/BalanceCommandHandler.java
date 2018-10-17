package com.vegesoft.moneytracker.account.handler;

import com.vegesoft.moneytracker.account.command.AddBalanceCommand;
import com.vegesoft.moneytracker.account.command.SubtractBalanceCommand;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface BalanceCommandHandler {

    Mono<Void> handle(final UUID accountId, final AddBalanceCommand addBalanceCommand);

    Mono<Void> handle(final UUID accountId, final SubtractBalanceCommand subtractBalanceCommand);
}
