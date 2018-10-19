package com.vegesoft.moneytracker.accounthistory.handler;

import com.vegesoft.moneytracker.accounthistory.command.AddIncomeCommand;
import com.vegesoft.moneytracker.accounthistory.domain.repository.IncomeRepository;
import com.vegesoft.moneytracker.accounthistory.handler.mapper.IncomeCommandMapper;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReactiveIncomeCommandHandler implements IncomeCommandHandler {

    private final IncomeCommandMapper incomeCommandMapper;
    private final IncomeRepository incomeRepository;

    @Autowired
    public ReactiveIncomeCommandHandler(final IncomeCommandMapper incomeCommandMapper,
        final IncomeRepository incomeRepository) {
        this.incomeCommandMapper = incomeCommandMapper;
        this.incomeRepository = incomeRepository;
    }

    @Override
    public Mono<Void> handle(final UUID uuid, final AddIncomeCommand addIncomeCommand) {
        return Mono.zip(Mono.just(uuid), Mono.just(addIncomeCommand))
            .map((zipped) -> incomeCommandMapper.map(zipped.getT1(), zipped.getT2()))
            .flatMap(incomeRepository::save)
            .then();
    }
}
