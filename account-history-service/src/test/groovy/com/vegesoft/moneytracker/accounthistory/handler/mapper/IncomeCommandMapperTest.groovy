package com.vegesoft.moneytracker.accounthistory.handler.mapper


import com.vegesoft.moneytracker.accounthistory.command.AddIncomeCommand
import org.junit.Test
import spock.lang.Specification

import java.time.LocalDateTime

class IncomeCommandMapperTest extends Specification {

    def mapper = new IncomeCommandMapper()

    @Test
    def "shouldMap_commandToIncome"() {
        given: "command"
            def id = UUID.randomUUID()
            def amount = BigDecimal.TEN
            def accountId = UUID.randomUUID()
            def createdAt = LocalDateTime.now()
            def command = new AddIncomeCommand(amount, accountId, createdAt)
        when: "mapping"
            def income = mapper.map(id, command)
        then: "all fields mapped"
            income.accountId == accountId
            income.createdAt == createdAt
            income.amount.value == amount
    }
}
