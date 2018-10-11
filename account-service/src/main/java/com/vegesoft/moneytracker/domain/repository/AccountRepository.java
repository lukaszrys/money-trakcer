package com.vegesoft.moneytracker.domain.repository;

import com.vegesoft.moneytracker.domain.Account;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface AccountRepository extends ReactiveCrudRepository<Account, UUID> {

}
