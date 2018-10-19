package com.vegesoft.moneytracker.account.client;

import com.vegesoft.moneytracker.account.client.data.ExpenseRequest;
import com.vegesoft.moneytracker.account.client.data.IncomeRequest;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface AccountHistoryClient {

    Mono<UUID> expense(final ExpenseRequest expenseRequest);

    Mono<UUID> income(final IncomeRequest incomeRequest);
}
