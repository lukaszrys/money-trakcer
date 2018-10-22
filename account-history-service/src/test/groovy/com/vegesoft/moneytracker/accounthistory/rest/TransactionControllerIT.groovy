package com.vegesoft.moneytracker.accounthistory.rest

import com.vegesoft.moneytracker.accounthistory.domain.Amount
import com.vegesoft.moneytracker.accounthistory.domain.Expense
import com.vegesoft.moneytracker.accounthistory.domain.Income
import com.vegesoft.moneytracker.accounthistory.domain.repository.ExpenseRepository
import com.vegesoft.moneytracker.accounthistory.domain.repository.IncomeRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

import java.time.LocalDateTime

@RunWith(SpringRunner.class)
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
    def "shouldGet_transactionViews"() {
        given: "create expense and income"
            def accountId = UUID.randomUUID()
            def expense = new Expense(UUID.randomUUID(), new Amount(BigDecimal.ONE), "type", LocalDateTime.now(), accountId)
            def income = new Income(UUID.randomUUID(), new Amount(BigDecimal.ZERO), LocalDateTime.now(), accountId)

            incomeRepository.save(income).block()
            expenseRepository.save(expense).block()
        when: "sending request"
            WebTestClient.ResponseSpec exchange = webTestClient.get()
                    .uri(API + "/" + accountId)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .exchange()
        then: "request processed correctly"
            exchange
                    .expectStatus()
                    .is2xxSuccessful()
                    .expectBody()
    }
}
