package com.vegesoft.moneytracker.account.rest;

import com.vegesoft.moneytracker.account.command.CreateAccountCommand;
import com.vegesoft.moneytracker.account.handler.AccountCommandHandler;
import com.vegesoft.moneytracker.account.rest.data.ResponseWrapper;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Validated
public class AccountController {

    private final AccountCommandHandler accountCommandHandler;

    public AccountController(final AccountCommandHandler accountCommandHandler) {
        this.accountCommandHandler = accountCommandHandler;
    }

    @PostMapping("/api/accounts")
    Mono<ResponseWrapper<UUID>> addAccount(@RequestBody final CreateAccountCommand createAccount) {
        final UUID uuid = UUID.randomUUID();
        return accountCommandHandler.add(uuid, createAccount).then(Mono.just(new ResponseWrapper<>(uuid)));
    }
}
