package com.vegesoft.moneytracker.account.query;

import com.vegesoft.moneytracker.account.query.view.AccountView;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface AccountQuery {

    Mono<AccountView> findById(final UUID id);
}
