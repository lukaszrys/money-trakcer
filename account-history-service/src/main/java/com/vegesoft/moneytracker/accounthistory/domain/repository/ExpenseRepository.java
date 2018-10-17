package com.vegesoft.moneytracker.accounthistory.domain.repository;

import com.vegesoft.moneytracker.accounthistory.domain.Expense;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends ReactiveCrudRepository<Expense, UUID> {

}
