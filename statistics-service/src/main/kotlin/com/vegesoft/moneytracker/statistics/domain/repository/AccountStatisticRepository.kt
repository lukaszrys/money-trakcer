package com.vegesoft.moneytracker.statistics.domain.repository

import com.vegesoft.moneytracker.statistics.domain.AccountStatistic
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountStatisticRepository : ReactiveCrudRepository<AccountStatistic, UUID>