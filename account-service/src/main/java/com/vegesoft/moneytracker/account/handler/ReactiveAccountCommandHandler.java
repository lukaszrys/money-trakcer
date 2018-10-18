package com.vegesoft.moneytracker.account.handler;

import com.vegesoft.moneytracker.account.command.CreateAccountCommand;
import com.vegesoft.moneytracker.account.domain.repository.AccountRepository;
import com.vegesoft.moneytracker.account.handler.mapper.AccountCommandMapper;
import java.util.UUID;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
class ReactiveAccountCommandHandler implements AccountCommandHandler {

    private final AccountRepository accountRepository;
    private final AccountCommandMapper accountCommandMapper;

    ReactiveAccountCommandHandler(final AccountRepository accountRepository,
        final AccountCommandMapper accountCommandMapper) {
        this.accountRepository = accountRepository;
        this.accountCommandMapper = accountCommandMapper;
    }

    @Override
    public Mono<Void> add(final UUID uuid, final CreateAccountCommand accountCommand) {
        return Mono.zip(Mono.just(uuid), Mono.just(accountCommand))
            .flatMap((data) -> accountRepository.save(accountCommandMapper.mapToAccount(data.getT1(), data.getT2())))
            .then();
    }
}
