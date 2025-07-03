package com.jetbrains.fintechpayment.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.fintechpayment.domain.usecase.GetPaymentsUseCase
import com.jetbrains.fintechpayment.domain.usecase.SendPaymentUseCase
import com.jetbrains.fintechpayment.model.Payment
import com.jetbrains.fintechpayment.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val sendPaymentUseCase: SendPaymentUseCase,
    private val getPaymentsUseCase: GetPaymentsUseCase
) : ViewModel() {

    var uiState by mutableStateOf<UiState<String>>(UiState.Idle)
        private set

    val transactions: Flow<List<Payment>> = getPaymentsUseCase()

    fun sendPayment(payment: Payment) {
        viewModelScope.launch {
            uiState = UiState.Loading

            val result = sendPaymentUseCase(payment)

            uiState = if (result) {
                UiState.Success("✅ Payment successful")
            } else {
                UiState.Error("❌ Payment failed")
            }
        }
    }
    fun resetState() {
        uiState = UiState.Idle
    }

}

