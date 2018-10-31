package com.vegesoft.moneytracker.accounthistory.rest;

import com.vegesoft.moneytracker.accounthistory.command.AddExpenseCommand;
import com.vegesoft.moneytracker.accounthistory.handler.ExpenseCommandHandler;
import com.vegesoft.moneytracker.accounthistory.rest.data.ResponseWrapper;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Validated
public class ExpenseController {

    private final ExpenseCommandHandler expenseCommandHandler;

    @Autowired
    public ExpenseController(final ExpenseCommandHandler expenseCommandHandler) {
        this.expenseCommandHandler = expenseCommandHandler;
    }

    @PostMapping("/api/expenses")
    Mono<ResponseWrapper<UUID>> addExpense(@RequestBody final AddExpenseCommand command) {
        final UUID uuid = UUID.randomUUID();
        return expenseCommandHandler.handle(uuid, command).then(Mono.just(new ResponseWrapper<>(uuid)));
    }
}
