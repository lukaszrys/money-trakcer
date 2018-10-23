package com.vegesoft.moneytracker.accounthistory.domain.repository;

import com.vegesoft.moneytracker.accounthistory.domain.Expense;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ExpenseRepository extends ReactiveCrudRepository<Expense, UUID> {

    Flux<Expense> findExpensesByAccountIdAndCreatedAtBetween(final UUID accountId, final LocalDateTime start,
        final LocalDateTime stop);
}
