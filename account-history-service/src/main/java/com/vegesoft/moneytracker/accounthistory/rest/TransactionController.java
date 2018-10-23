package com.vegesoft.moneytracker.accounthistory.rest;

import com.vegesoft.moneytracker.accounthistory.query.TransactionQuery;
import com.vegesoft.moneytracker.accounthistory.query.view.TransactionView;
import com.vegesoft.moneytracker.accounthistory.rest.data.GetTransactionForAccountParams;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Validated
public class TransactionController {

    private final TransactionQuery transactionQuery;

    @Autowired
    public TransactionController(final TransactionQuery transactionQuery) {
        this.transactionQuery = transactionQuery;
    }

    @GetMapping("/api/transactions/{accountId}")
    Flux<TransactionView> getTransactionForAccount(@PathVariable final UUID accountId,
        GetTransactionForAccountParams params) {
        return transactionQuery.findTransactionsByAccountId(accountId, params.getFrom(), params.getTo());
    }
}
