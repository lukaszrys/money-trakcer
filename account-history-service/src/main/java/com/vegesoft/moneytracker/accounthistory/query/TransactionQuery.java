package com.vegesoft.moneytracker.accounthistory.query;

import com.vegesoft.moneytracker.accounthistory.query.view.TransactionView;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface TransactionQuery {

    Flux<TransactionView> findTransactionsByAccountId(final UUID accountId, final LocalDateTime from,
        final LocalDateTime to);
}
