package com.jetbrains.fintechpayment.domain.usecase

import com.jetbrains.fintechpayment.data.repository.PaymentRepository
import com.jetbrains.fintechpayment.model.Payment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPaymentsUseCase(
    private val repository: PaymentRepository
) {
    operator fun invoke(): Flow<List<Payment>> = repository.getAllPayments()
        .map { list ->
            list.sortedByDescending { it.timestamp }
        }

}
