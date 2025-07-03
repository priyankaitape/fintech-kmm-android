package com.jetbrains.fintechpayment.domain.usecase

import com.jetbrains.fintechpayment.data.repository.PaymentRepository
import com.jetbrains.fintechpayment.model.Payment

class SendPaymentUseCase(
    private val repository: PaymentRepository
) {
    suspend operator fun invoke(payment: Payment): Boolean {
        return repository.sendPayment(payment)
    }
}
