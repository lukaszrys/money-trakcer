package com.vegesoft.moneytracker.account.client;

import com.vegesoft.moneytracker.account.client.data.ExpenseRequest;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface AccountHistoryClient {

    Mono<UUID> expense(final ExpenseRequest expenseRequest);
}
