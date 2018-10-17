package com.vegesoft.moneytracker.handler;

import com.vegesoft.moneytracker.command.AddBalanceCommand;
import com.vegesoft.moneytracker.command.SubtractBalanceCommand;
import com.vegesoft.moneytracker.domain.Account;
import com.vegesoft.moneytracker.domain.Balance;
import com.vegesoft.moneytracker.domain.repository.AccountRepository;
import java.util.UUID;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
class ReactiveBalanceCommandHandler implements BalanceCommandHandler {

    private final AccountRepository accountRepository;

    @Autowired
    ReactiveBalanceCommandHandler(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<Void> handle(final UUID accountId, final AddBalanceCommand addBalanceCommand) {
        return Mono.just(accountId).flatMap(accountRepository::findById).map(account -> {
            account.addBalance(new Balance(addBalanceCommand.getAmount()));
            return account;
        }).map((Function<Account, Object>) accountRepository::save).then();
    }

    @Override
    public Mono<Void> handle(final UUID accountId, final SubtractBalanceCommand subtractBalanceCommand) {
        return Mono.just(accountId).flatMap(accountRepository::findById).map(account -> {
            account.subtractBalance(new Balance(subtractBalanceCommand.getAmount()));
            return account;
        }).map((Function<Account, Object>) accountRepository::save).then();
    }
}
