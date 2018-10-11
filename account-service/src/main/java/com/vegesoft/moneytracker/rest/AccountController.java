package com.vegesoft.moneytracker.rest;

import com.vegesoft.moneytracker.command.CreateAccountCommand;
import com.vegesoft.moneytracker.handler.AccountCommandHandler;
import com.vegesoft.moneytracker.rest.data.ResponseWrapper;
import java.util.UUID;
import org.springframework.stereotype.Controller;
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
        return Mono.just(UUID.randomUUID()).map((uuid -> {
            accountCommandHandler.add(uuid, createAccount);
            return new ResponseWrapper<>(uuid);
        }));

    }
}
