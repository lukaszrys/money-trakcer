package com.vegesoft.moneytracker.rest;

import com.vegesoft.moneytracker.command.AddBalanceCommand;
import com.vegesoft.moneytracker.command.SubtractBalanceCommand;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Validated
public class BalanceController {

    @PostMapping("/api/accounts/{accountId}/balance/add")
    Mono<Void> add(@PathVariable final UUID accountId,
        @RequestBody final AddBalanceCommand addBalanceCommand) {
        return Mono.empty();
    }

    @PostMapping("/api/accounts/{accountId}/balance/subtract")
    Mono<Void> subtract(@PathVariable final UUID accountId,
        @RequestBody final SubtractBalanceCommand subtractBalanceCommand) {
        return Mono.empty();
    }
}
