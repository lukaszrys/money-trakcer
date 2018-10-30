package com.vegesoft.moneytracker.statistics.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.util.*

@Document
data class AccountStatistic(@Id
                       val id: UUID,
                       val range: AccountStatisticRange,
                       val accountId: UUID,
                       var amount: BigDecimal,
                       val incomes: MutableList<Transaction>,
                       val expenses: MutableList<Transaction>)