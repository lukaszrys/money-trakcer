package com.vegesoft.moneytracker.account.query;

import com.vegesoft.moneytracker.account.domain.repository.AccountRepository;
import com.vegesoft.moneytracker.account.query.view.AccountView;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
class MongoRepositoryAccountQuery implements AccountQuery {

    private final AccountRepository accountRepository;

    @Autowired
    public MongoRepositoryAccountQuery(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    @Override
    public Mono<AccountView> findById(final UUID id) {
        return accountRepository.findById(id)
            .map(account -> new AccountView(account.getId(), account.getBalance().getAmount()));
    }
}
