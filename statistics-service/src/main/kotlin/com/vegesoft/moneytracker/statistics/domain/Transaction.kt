package com.vegesoft.moneytracker.statistics.domain

import java.math.BigDecimal

data class Transaction(val amount: BigDecimal,
                       val type: String)