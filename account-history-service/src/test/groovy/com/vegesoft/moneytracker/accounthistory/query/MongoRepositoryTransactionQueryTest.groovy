package com.vegesoft.moneytracker.accounthistory.query

import com.vegesoft.moneytracker.accounthistory.domain.Amount
import com.vegesoft.moneytracker.accounthistory.domain.Expense
import com.vegesoft.moneytracker.accounthistory.domain.Income
import com.vegesoft.moneytracker.accounthistory.domain.repository.ExpenseRepository
import com.vegesoft.moneytracker.accounthistory.domain.repository.IncomeRepository
import org.junit.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import spock.lang.Specification

import java.time.LocalDateTime

class MongoRepositoryTransactionQueryTest extends Specification {

    def expenseRepository = Mock(ExpenseRepository)
    def incomeRepository = Mock(IncomeRepository)
    def transactionQuery = new MongoRepositoryTransactionQuery(expenseRepository, incomeRepository)

    @Test
    def "shouldFindTransactions_withExpenseIncome_byId"() {
        given:
            def accountId = UUID.randomUUID()
            def income = createIncome(accountId)
            def expense = createExpense(accountId)
        when:
            def result = StepVerifier.create(transactionQuery.findTransactionsByAccountId(accountId))
        then:
            expenseRepository.findExpensesByAccountId(accountId) >> Flux.just(expense)
            incomeRepository.findIncomesByAccountId(accountId) >> Flux.just(income)
            result.assertNext { expenseView -> expense.type == expenseView.type }.assertNext { incomeView
                ->
                MongoRepositoryTransactionQuery.INCOME_TYPE == incomeView.type
            }.expectComplete().verify()

    }

    @Test
    def "shouldFindTransactions_withExpense_byId"() {
        given:
            def accountId = UUID.randomUUID()
            def expense = createExpense(accountId)
        when:
            def result = StepVerifier.create(transactionQuery.findTransactionsByAccountId(accountId))
        then:
            expenseRepository.findExpensesByAccountId(accountId) >> Flux.just(expense)
            incomeRepository.findIncomesByAccountId(accountId) >> Flux.empty()
            result.assertNext { transactionView
                ->
                expense.createdAt == transactionView.createdAt
                expense.amount.value == transactionView.amount
                expense.type == transactionView.type
            }.expectComplete().verify()
    }

    @Test
    def "shouldFindTransactions_withIncome_byId"() {
        given:
            def accountId = UUID.randomUUID()
            def income = createIncome(accountId)
        when:
            def result = StepVerifier.create(transactionQuery.findTransactionsByAccountId(accountId))
        then:
            expenseRepository.findExpensesByAccountId(accountId) >> Flux.empty()
            incomeRepository.findIncomesByAccountId(accountId) >> Flux.just(income)
            result.assertNext { transactionView ->
                income.createdAt == transactionView.createdAt
                income.amount.value == transactionView.amount
                MongoRepositoryTransactionQuery.INCOME_TYPE == transactionView.type
            }.expectComplete().verify()
    }

    @Test
    def "shouldNotFindTransactions_byId"() {
        given:
            def accountId = UUID.randomUUID()
        when:
            def result = StepVerifier.create(transactionQuery.findTransactionsByAccountId(accountId))
        then:
            expenseRepository.findExpensesByAccountId(accountId) >> Flux.empty()
            incomeRepository.findIncomesByAccountId(accountId) >> Flux.empty()
            result.expectComplete().verify()
    }

    private Expense createExpense(UUID accountId) {
        new Expense(UUID.randomUUID(), new Amount(BigDecimal.ONE), "type", LocalDateTime.now(), accountId)
    }

    private Income createIncome(UUID accountId) {
        new Income(UUID.randomUUID(), new Amount(BigDecimal.ZERO), LocalDateTime.now(), accountId)
    }
}
