package com.vegesoft.moneytracker.account.handler;

import com.vegesoft.moneytracker.account.command.CreateAccountCommand;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface AccountCommandHandler {

    Mono<Void> add(final UUID uuid, final CreateAccountCommand accountCommand);
}
