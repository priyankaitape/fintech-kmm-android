package com.jetbrains.fintechpayment.datasource.remote

import com.jetbrains.fintechpayment.data.remote.PaymentRemoteDataSource
import com.jetbrains.fintechpayment.model.Payment

class FakePaymentRemoteDataSource : PaymentRemoteDataSource {
    override suspend fun sendPaymentToApi(payment: Payment): Boolean {
        return true // No-op for BDD testing
    }
}