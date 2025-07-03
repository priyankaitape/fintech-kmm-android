package com.jetbrains.fintechpayment.data.remote

import com.jetbrains.fintechpayment.model.Payment

interface PaymentRemoteDataSource {
    suspend fun sendPaymentToApi(payment: Payment): Boolean
}
