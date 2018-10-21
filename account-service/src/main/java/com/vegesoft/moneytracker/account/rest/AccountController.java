package com.vegesoft.moneytracker.account.rest;

import com.vegesoft.moneytracker.account.command.CreateAccountCommand;
import com.vegesoft.moneytracker.account.exception.NotFoundAccountException;
import com.vegesoft.moneytracker.account.handler.AccountCommandHandler;
import com.vegesoft.moneytracker.account.query.AccountQuery;
import com.vegesoft.moneytracker.account.query.view.AccountView;
import com.vegesoft.moneytracker.account.rest.data.ResponseWrapper;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Validated
public class AccountController {

    private final AccountCommandHandler accountCommandHandler;
    private final AccountQuery accountQuery;

    public AccountController(final AccountCommandHandler accountCommandHandler, final AccountQuery accountQuery) {
        this.accountCommandHandler = accountCommandHandler;
        this.accountQuery = accountQuery;
    }

    @PostMapping("/api/accounts")
    Mono<ResponseWrapper<UUID>> addAccount(@RequestBody final CreateAccountCommand createAccount) {
        final UUID uuid = UUID.randomUUID();
        return accountCommandHandler.add(uuid, createAccount).then(Mono.just(new ResponseWrapper<>(uuid)));
    }

    @GetMapping("/api/accounts/{id}")
    Mono<AccountView> getAccount(@PathVariable UUID id) {
        return accountQuery.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundAccountException("Could not find account with id " + id)));
    }
}
