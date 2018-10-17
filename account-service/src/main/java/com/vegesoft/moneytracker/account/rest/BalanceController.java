package com.vegesoft.moneytracker.account.rest;

import com.vegesoft.moneytracker.account.command.AddBalanceCommand;
import com.vegesoft.moneytracker.account.command.SubtractBalanceCommand;
import com.vegesoft.moneytracker.account.handler.BalanceCommandHandler;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Validated
public class BalanceController {

    private final BalanceCommandHandler balanceCommandHandler;

    @Autowired
    public BalanceController(final BalanceCommandHandler balanceCommandHandler) {
        this.balanceCommandHandler = balanceCommandHandler;
    }


    @PostMapping("/api/accounts/{accountId}/balance/add")
    Mono<Void> add(@PathVariable final UUID accountId, @RequestBody final AddBalanceCommand addBalanceCommand) {
        return balanceCommandHandler.handle(accountId, addBalanceCommand);
    }

    @PostMapping("/api/accounts/{accountId}/balance/subtract")
    Mono<Void> subtract(@PathVariable final UUID accountId,
        @RequestBody final SubtractBalanceCommand subtractBalanceCommand) {
        return balanceCommandHandler.handle(accountId, subtractBalanceCommand);
    }
}
