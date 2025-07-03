package com.jetbrains.fintechpayment.di

import com.jetbrains.fintechpayment.data.local.PaymentLocalDataSource
import com.jetbrains.fintechpayment.data.remote.PaymentRemoteDataSource
import com.jetbrains.fintechpayment.data.remote.PaymentService
import com.jetbrains.fintechpayment.data.repository.PaymentRepository
import com.jetbrains.fintechpayment.data.repository.PaymentRepositoryImpl
import com.jetbrains.fintechpayment.datasource.firestore.FirestoreDataSource
import com.jetbrains.fintechpayment.domain.usecase.GetPaymentsUseCase
import com.jetbrains.fintechpayment.domain.usecase.SendPaymentUseCase
import com.jetbrains.fintechpayment.viewmodel.PaymentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Local Firestore
    single<PaymentLocalDataSource> { FirestoreDataSource() }

    // Remote API
    single<PaymentRemoteDataSource> { PaymentService() }

    // Repository
    single<PaymentRepository> {
        PaymentRepositoryImpl(
            get<PaymentRemoteDataSource>(),
            get<PaymentLocalDataSource>()
        )
    }

    // Use Cases
    single { SendPaymentUseCase(get()) }
    single { GetPaymentsUseCase(get()) }

    // ViewModel
    viewModel { PaymentViewModel(get(), get()) }
}
