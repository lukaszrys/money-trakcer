package com.vegesoft.moneytracker.accounthistory.domain.repository;

import com.vegesoft.moneytracker.accounthistory.domain.Income;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends ReactiveCrudRepository<Income, UUID> {

}
