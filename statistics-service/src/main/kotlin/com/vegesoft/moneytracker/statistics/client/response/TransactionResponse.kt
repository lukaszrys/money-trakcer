package com.vegesoft.moneytracker.statistics.client.response

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class TransactionResponse(val amount: BigDecimal,
                               val type: String,
                               val createdAt: LocalDateTime,
                               val accountId: UUID)