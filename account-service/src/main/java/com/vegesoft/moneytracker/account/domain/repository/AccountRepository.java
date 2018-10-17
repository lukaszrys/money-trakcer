package com.vegesoft.moneytracker.account.domain.repository;

import com.vegesoft.moneytracker.account.domain.Account;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface AccountRepository extends ReactiveCrudRepository<Account, UUID> {

}
