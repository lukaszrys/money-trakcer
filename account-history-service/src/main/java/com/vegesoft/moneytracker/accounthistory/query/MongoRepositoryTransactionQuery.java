package com.vegesoft.moneytracker.accounthistory.query;

import com.vegesoft.moneytracker.accounthistory.domain.repository.ExpenseRepository;
import com.vegesoft.moneytracker.accounthistory.domain.repository.IncomeRepository;
import com.vegesoft.moneytracker.accounthistory.query.view.TransactionView;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
class MongoRepositoryTransactionQuery implements TransactionQuery {

    public static final String INCOME_TYPE = "income";

    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;

    @Autowired
    public MongoRepositoryTransactionQuery(final ExpenseRepository expenseRepository,
        final IncomeRepository incomeRepository) {
        this.expenseRepository = expenseRepository;
        this.incomeRepository = incomeRepository;
    }

    @Override
    public Flux<TransactionView> findTransactionsByAccountId(final UUID accountId, final LocalDateTime from,
        final LocalDateTime to) {
        final Flux<TransactionView> expenses = expenseRepository.findExpensesByAccountIdAndCreatedAtBetween(accountId,
            from, to)
            .map((expense -> new TransactionView(expense.getAmount().getValue(), expense.getType(),
                expense.getCreatedAt(), accountId)));
        final Flux<TransactionView> incomes = incomeRepository.findIncomesByAccountIdAndCreatedAtBetween(accountId,
            from, to)
            .map((expense -> new TransactionView(expense.getAmount().getValue(), INCOME_TYPE, expense.getCreatedAt(),
                accountId)));
        return expenses.mergeWith(incomes);
    }
}
