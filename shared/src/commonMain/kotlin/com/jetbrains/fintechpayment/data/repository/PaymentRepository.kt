package com.jetbrains.fintechpayment.data.repository

import com.jetbrains.fintechpayment.model.Payment
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    suspend fun sendPayment(payment: Payment): Boolean
    fun getAllPayments(): Flow<List<Payment>>
}
