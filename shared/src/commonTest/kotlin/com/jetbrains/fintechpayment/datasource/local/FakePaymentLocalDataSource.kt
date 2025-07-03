package com.jetbrains.fintechpayment.datasource.local

import com.jetbrains.fintechpayment.data.local.PaymentLocalDataSource
import com.jetbrains.fintechpayment.model.Payment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakePaymentLocalDataSource : PaymentLocalDataSource {
    private val payments = MutableStateFlow<List<Payment>>(emptyList())

    fun seedSampleTransactions() {
        payments.value = listOf(
            Payment("alice@example.com", 100.0, "USD",1751961600000 ),
            Payment("bob@example.com", 250.0, "EUR", 1751976000000)
        )
    }

    override fun savePayment(payment: Payment, onResult: (Boolean) -> Unit) {
        payments.value = payments.value + payment
        onResult(true)
    }

    override fun getPaymentsFlow(): Flow<List<Payment>> = payments.asStateFlow()

}
