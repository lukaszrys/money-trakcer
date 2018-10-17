package com.vegesoft.moneytracker.account.history.domain.repository;

import com.vegesoft.moneytracker.account.history.domain.Expense;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends ReactiveCrudRepository<Expense, UUID> {

}
