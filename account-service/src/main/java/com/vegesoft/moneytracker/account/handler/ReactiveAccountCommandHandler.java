package com.vegesoft.moneytracker.account.handler;

import com.vegesoft.moneytracker.account.command.CreateAccountCommand;
import com.vegesoft.moneytracker.account.domain.Account;
import com.vegesoft.moneytracker.account.domain.Balance;
import com.vegesoft.moneytracker.account.domain.repository.AccountRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
class ReactiveAccountCommandHandler implements AccountCommandHandler {

    private final AccountRepository accountRepository;

    ReactiveAccountCommandHandler(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<Void> add(final UUID uuid, final CreateAccountCommand accountCommand) {
        return Mono.zip(Mono.just(uuid), Mono.just(accountCommand))
            .map((data) -> accountRepository.save(mapToAccount(data.getT1(), data.getT2())))
            .then();
    }

    private Account mapToAccount(final UUID uuid, final CreateAccountCommand command) {
        return new Account(uuid, new Balance(command.getInitialValue()));
    }
}
