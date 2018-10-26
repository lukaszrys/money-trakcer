package com.vegesoft.moneytracker.statistics.domain

import java.time.LocalDateTime

data class AccountStatisticRange(val from: LocalDateTime,
                                 val to: LocalDateTime)