package com.jetbrains.fintechpayment.data.repository

import android.util.Log
import com.jetbrains.fintechpayment.data.local.PaymentLocalDataSource
import com.jetbrains.fintechpayment.data.remote.PaymentRemoteDataSource
import com.jetbrains.fintechpayment.model.Payment
import kotlinx.coroutines.flow.Flow

class PaymentRepositoryImpl(
    private val remoteDataSource: PaymentRemoteDataSource,
    private val localDataSource: PaymentLocalDataSource
) : PaymentRepository {

    override suspend fun sendPayment(payment: Payment): Boolean {
        val apiSuccess = remoteDataSource.sendPaymentToApi(payment)
        if (apiSuccess) {
            localDataSource.savePayment(payment) { success ->
                Log.d("FintechApp", "✅ Firestore saved: $success")
            }
        }
        return apiSuccess
    }

    override fun getAllPayments(): Flow<List<Payment>> = localDataSource.getPaymentsFlow()
}

