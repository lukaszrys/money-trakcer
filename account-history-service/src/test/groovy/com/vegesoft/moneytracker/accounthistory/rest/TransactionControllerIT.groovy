package com.vegesoft.moneytracker.accounthistory.rest

import com.vegesoft.moneytracker.accounthistory.domain.Amount
import com.vegesoft.moneytracker.accounthistory.domain.Expense
import com.vegesoft.moneytracker.accounthistory.domain.Income
import com.vegesoft.moneytracker.accounthistory.domain.repository.ExpenseRepository
import com.vegesoft.moneytracker.accounthistory.domain.repository.IncomeRepository
import com.vegesoft.moneytracker.accounthistory.query.view.TransactionView
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.FluxExchangeResult
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.test.StepVerifier
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerIT extends Specification {

    private static final String API = "/api/transactions"

    @Autowired
    private WebTestClient webTestClient
    @Autowired
    private ExpenseRepository expenseRepository
    @Autowired
    private IncomeRepository incomeRepository

    @Test
    def "shouldGet_transactionViews_withoutDates"() {
        given: "create expense and income"
            def accountId = UUID.randomUUID()
            def expense = new Expense(UUID.randomUUID(), new Amount(BigDecimal.ONE), "type", LocalDateTime.now(), accountId)
            def income = new Income(UUID.randomUUID(), new Amount(BigDecimal.ZERO), LocalDateTime.now(), accountId)

            incomeRepository.save(income).block()
            expenseRepository.save(expense).block()
        when: "sending request"
            WebTestClient.ResponseSpec exchange = webTestClient.get()
                    .uri(API + "/" + accountId)
                    .exchange()
        then: "request processed correctly"
            FluxExchangeResult<TransactionView> result = exchange
                    .expectStatus()
                    .is2xxSuccessful()
                    .returnResult(TransactionView.class)

            StepVerifier.create(result.responseBody).assertNext {viewIncome -> viewIncome != null }
                    .assertNext{expenseIncome -> expenseIncome != null }
                    .expectComplete().verify()
        cleanup: "clean database"
            incomeRepository.deleteAll().block()
            expenseRepository.deleteAll().block()
    }

    @Test
    def "shouldGet_transactionViews_withDates"() {
        given: "create expense and income"
            def accountId = UUID.randomUUID()
            def cratedAt = LocalDateTime.now()
            def expense = new Expense(UUID.randomUUID(), new Amount(BigDecimal.ONE), "type", cratedAt, accountId)
            def income = new Income(UUID.randomUUID(), new Amount(BigDecimal.ZERO), cratedAt, accountId)

            incomeRepository.save(income).block()
            expenseRepository.save(expense).block()
        when: "sending request"
            WebTestClient.ResponseSpec exchange = webTestClient.get()
                    .uri { builder ->
                builder.path(API + "/" + accountId)
                        .queryParam("from", cratedAt.minusDays(1))
                        .queryParam("to", cratedAt.plusDays(1))
                        .build()
                        }
            .exchange()
        then: "request processed correctly"
            FluxExchangeResult<TransactionView> result = exchange
                    .expectStatus()
                    .is2xxSuccessful()
                    .returnResult(TransactionView.class)

            StepVerifier.create(result.responseBody).assertNext {viewIncome -> viewIncome != null }
                    .assertNext{expenseIncome -> expenseIncome != null }
                    .expectComplete().verify()

        cleanup: "clean database"
            incomeRepository.deleteAll().block()
            expenseRepository.deleteAll().block()
    }
}
