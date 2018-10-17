package com.vegesoft.moneytracker.accounthistory.handler


import com.vegesoft.moneytracker.accounthistory.handler.mapper.ExpenseCommandMapper
import com.vegesoft.moneytracker.accounthistory.command.AddExpenseCommand
import com.vegesoft.moneytracker.accounthistory.domain.Amount
import com.vegesoft.moneytracker.accounthistory.domain.Expense
import com.vegesoft.moneytracker.accounthistory.domain.repository.ExpenseRepository
import org.junit.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

import java.time.LocalDateTime

class ReactiveExpenseCommandHandlerTest extends Specification {

    def expenseRepository = Mock(ExpenseRepository)
    def mapper = Mock(ExpenseCommandMapper)
    def handler = new ReactiveExpenseCommandHandler(mapper, expenseRepository)

    @Test
    def "shouldAdd_expense"() {
        given: "command with id"
            def expense = new Expense(UUID.randomUUID(), new Amount(BigDecimal.TEN), "type", LocalDateTime.now(), UUID.randomUUID())
            def command = new AddExpenseCommand(BigDecimal.TEN, "food", UUID.randomUUID(), LocalDateTime.now())
            def id = UUID.randomUUID()
        when: "handle command"
            def handle = handler.handle(id, command)
            StepVerifier.create(handle).expectComplete().verify()
        then: "command has been handled"
            1 * mapper.map(id, command) >> expense
            1 * expenseRepository.save(expense) >> Mono.just(expense)
    }
}
