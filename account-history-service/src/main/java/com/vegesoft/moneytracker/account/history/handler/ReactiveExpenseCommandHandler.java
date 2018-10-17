package com.vegesoft.moneytracker.account.history.handler;

import com.vegesoft.moneytracker.account.history.command.AddExpenseCommand;
import com.vegesoft.moneytracker.account.history.domain.repository.ExpenseRepository;
import com.vegesoft.moneytracker.account.history.handler.mapper.ExpenseCommandMapper;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReactiveExpenseCommandHandler implements ExpenseCommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(ReactiveExpenseCommandHandler.class);

    private final ExpenseCommandMapper mapper;
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ReactiveExpenseCommandHandler(final ExpenseCommandMapper mapper, final ExpenseRepository expenseRepository) {
        this.mapper = mapper;
        this.expenseRepository = expenseRepository;
    }


    @Override
    public Mono<Void> handle(final UUID uuid, final AddExpenseCommand addExpenseCommand) {
        return Mono.zip(Mono.just(uuid), Mono.just(addExpenseCommand))
            .map((zipped) -> mapper.map(zipped.getT1(), zipped.getT2()))
            .flatMap(expenseRepository::save)
            .then();
    }
}
