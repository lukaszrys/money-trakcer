package com.vegesoft.moneytracker.account.handler;

import com.vegesoft.moneytracker.account.client.AccountHistoryClient;
import com.vegesoft.moneytracker.account.command.AddBalanceCommand;
import com.vegesoft.moneytracker.account.command.SubtractBalanceCommand;
import com.vegesoft.moneytracker.account.domain.Balance;
import com.vegesoft.moneytracker.account.domain.repository.AccountRepository;
import com.vegesoft.moneytracker.account.handler.mapper.BalanceCommandMapper;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
class ReactiveBalanceCommandHandler implements BalanceCommandHandler {

    private final AccountRepository accountRepository;
    private final AccountHistoryClient accountHistoryClient;
    private final BalanceCommandMapper balanceCommandMapper;

    @Autowired
    ReactiveBalanceCommandHandler(final AccountRepository accountRepository,
        final AccountHistoryClient accountHistoryClient, final BalanceCommandMapper balanceCommandMapper) {
        this.accountRepository = accountRepository;
        this.accountHistoryClient = accountHistoryClient;
        this.balanceCommandMapper = balanceCommandMapper;
    }

    @Override
    public Mono<Void> handle(final UUID accountId, final AddBalanceCommand addBalanceCommand) {
        return Mono.just(accountId)
            .flatMap(accountRepository::findById)
            .map(account -> {
                account.addBalance(new Balance(addBalanceCommand.getAmount()));
                return account;
            })
            .flatMap(accountRepository::save)
            .flatMap(account -> accountHistoryClient.income(
                balanceCommandMapper.incomeRequest(accountId, addBalanceCommand)))
            .then();
    }

    @Override
    public Mono<Void> handle(final UUID accountId, final SubtractBalanceCommand subtractBalanceCommand) {
        return Mono.just(accountId)
            .flatMap(accountRepository::findById)
            .map(account -> {
                account.subtractBalance(new Balance(subtractBalanceCommand.getAmount()));
                return account;
            })
            .flatMap(accountRepository::save)
            .flatMap((account -> accountHistoryClient.expense(
                balanceCommandMapper.expenseRequest(accountId, subtractBalanceCommand))))
            .then();
    }
}
