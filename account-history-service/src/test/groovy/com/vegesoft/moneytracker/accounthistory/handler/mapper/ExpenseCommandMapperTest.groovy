package com.vegesoft.moneytracker.accounthistory.handler.mapper

import com.vegesoft.moneytracker.accounthistory.command.AddExpenseCommand
import org.junit.Test
import spock.lang.Specification

import java.time.LocalDateTime

class ExpenseCommandMapperTest extends Specification {

    def mapper = new ExpenseCommandMapper()

    @Test
    def "shouldMap_commandToExpense"() {
        given: "command"
            def id = UUID.randomUUID()
            def amount = BigDecimal.TEN
            def type = "type"
            def accountId = UUID.randomUUID()
            def createdAt = LocalDateTime.now()
            def command = new AddExpenseCommand(amount, type, accountId, createdAt)
        when: "mapping"
            def expense = mapper.map(id, command)
        then: "all fields mapped"
            expense.accountId == accountId
            expense.type == type
            expense.createdAt == createdAt
            expense.amount.value == amount
    }
}
