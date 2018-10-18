package com.vegesoft.moneytracker.accounthistory.rest

import com.vegesoft.moneytracker.accounthistory.command.AddExpenseCommand
import com.vegesoft.moneytracker.accounthistory.domain.repository.ExpenseRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

import java.time.LocalDateTime

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExpenseControllerIT extends Specification {

    private static final String API = "/api/expenses"

    @Autowired
    private WebTestClient webTestClient
    @Autowired
    private ExpenseRepository expenseRepository


    @Test
    def "shouldSave_expense"() {
        given: "create command"
            def command = new AddExpenseCommand(BigDecimal.TEN, "type", UUID.randomUUID(), LocalDateTime.now())
        when: "sending request"
            def exchange = webTestClient.post()
                    .uri(API)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .body(Mono.just(command), AddExpenseCommand.class)
                    .exchange()
        then: "request processed correctly"
            exchange
                    .expectStatus()
                    .is2xxSuccessful()
                    .expectBody()
            StepVerifier.create(expenseRepository.findAll()).assertNext { expense -> expense != null }
                    .expectComplete().verify()
    }
}
