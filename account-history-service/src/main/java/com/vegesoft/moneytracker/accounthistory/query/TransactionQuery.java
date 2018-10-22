package com.vegesoft.moneytracker.accounthistory.query;

import com.vegesoft.moneytracker.accounthistory.query.view.TransactionView;
import java.util.UUID;
import reactor.core.publisher.Flux;

public interface TransactionQuery {

    Flux<TransactionView> findTransactionsByAccountId(final UUID accountId);
}
