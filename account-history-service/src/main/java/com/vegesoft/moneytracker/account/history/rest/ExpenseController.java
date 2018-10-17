package com.vegesoft.moneytracker.account.history.rest;

import com.vegesoft.moneytracker.account.history.command.AddExpenseCommand;
import com.vegesoft.moneytracker.account.history.handler.ExpenseCommandHandler;
import com.vegesoft.moneytracker.account.history.rest.data.ResponseWrapper;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
        return Mono.just(UUID.randomUUID()).map((uuid -> {
            expenseCommandHandler.handle(uuid, command);
            return new ResponseWrapper<>(uuid);
        }));

    }
}
