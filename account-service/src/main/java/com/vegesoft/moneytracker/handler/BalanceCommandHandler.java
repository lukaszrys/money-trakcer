package com.vegesoft.moneytracker.handler;

import com.vegesoft.moneytracker.command.AddBalanceCommand;
import com.vegesoft.moneytracker.command.SubtractBalanceCommand;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface BalanceCommandHandler {

    Mono<Void> addBalance(final UUID accountId, final AddBalanceCommand addBalanceCommand);

    Mono<Void> subtractBalance(final UUID accountId, final SubtractBalanceCommand subtractBalanceCommand);
}
