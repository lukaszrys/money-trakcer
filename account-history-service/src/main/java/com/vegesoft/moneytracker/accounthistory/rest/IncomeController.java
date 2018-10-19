package com.vegesoft.moneytracker.accounthistory.rest;

import com.vegesoft.moneytracker.accounthistory.command.AddIncomeCommand;
import com.vegesoft.moneytracker.accounthistory.handler.IncomeCommandHandler;
import com.vegesoft.moneytracker.accounthistory.rest.data.ResponseWrapper;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Validated
public class IncomeController {

    private final IncomeCommandHandler incomeCommandHandler;

    @Autowired
    public IncomeController(final IncomeCommandHandler incomeCommandHandler) {
        this.incomeCommandHandler = incomeCommandHandler;
    }

    @PostMapping("/api/incomes")
    Mono<ResponseWrapper<UUID>> addIncome(@RequestBody final AddIncomeCommand command) {
        final UUID uuid = UUID.randomUUID();
        return incomeCommandHandler.handle(uuid, command).then(Mono.just(new ResponseWrapper<>(uuid)));
    }
}
