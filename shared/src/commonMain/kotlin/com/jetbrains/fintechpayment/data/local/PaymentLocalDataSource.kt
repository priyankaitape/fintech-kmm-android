package com.jetbrains.fintechpayment.data.local

import com.jetbrains.fintechpayment.model.Payment
import kotlinx.coroutines.flow.Flow

interface PaymentLocalDataSource {
    fun savePayment(payment: Payment, onResult: (Boolean) -> Unit)
    fun getPaymentsFlow(): Flow<List<Payment>>
}