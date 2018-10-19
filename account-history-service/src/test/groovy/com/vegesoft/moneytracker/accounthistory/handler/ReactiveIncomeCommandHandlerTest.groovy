package com.vegesoft.moneytracker.accounthistory.handler


import com.vegesoft.moneytracker.accounthistory.command.AddIncomeCommand
import com.vegesoft.moneytracker.accounthistory.domain.Amount
import com.vegesoft.moneytracker.accounthistory.domain.Income
import com.vegesoft.moneytracker.accounthistory.domain.repository.IncomeRepository
import com.vegesoft.moneytracker.accounthistory.handler.mapper.IncomeCommandMapper
import org.junit.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

import java.time.LocalDateTime

class ReactiveIncomeCommandHandlerTest extends Specification {

    def incomeRepository = Mock(IncomeRepository)
    def mapper = Mock(IncomeCommandMapper)
    def handler = new ReactiveIncomeCommandHandler(mapper, incomeRepository)

    @Test
    def "shouldAdd_income"() {
        given: "command with id"
            def income = new Income(UUID.randomUUID(), new Amount(BigDecimal.TEN), LocalDateTime.now(), UUID.randomUUID())
            def command = new AddIncomeCommand(BigDecimal.TEN, UUID.randomUUID(), LocalDateTime.now())
            def id = UUID.randomUUID()
        when: "handle command"
            def handle = handler.handle(id, command)
            StepVerifier.create(handle).expectComplete().verify()
        then: "command has been handled"
            1 * mapper.map(id, command) >> income
            1 * incomeRepository.save(income) >> Mono.just(income)
    }
}
