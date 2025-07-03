package com.jetbrains.fintechpayment.ui.screens

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.jetbrains.fintechpayment.model.Payment
import com.jetbrains.fintechpayment.state.UiState
import com.jetbrains.fintechpayment.util.getCurrentTimestamp
import com.jetbrains.fintechpayment.viewmodel.PaymentViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SendPaymentScreenContainer(
    viewModel: PaymentViewModel = koinViewModel(),
    snackbarHostState: SnackbarHostState,
    onSuccess: () -> Unit
) {
    val uiState = viewModel.uiState

    // Launch side effect when uiState changes
    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Success -> {
                snackbarHostState.showSnackbar(
                    message = uiState.data
                )
                //delay(2000) // optional, give time to read
                viewModel.resetState()
                onSuccess()
            }

            is UiState.Error -> {
                snackbarHostState.showSnackbar(uiState.message)
                viewModel.resetState()
            }

            else -> {}
        }
    }

    SendPaymentScreen(
        onSendClick = { email, amount, currency ->
            val payment = Payment(email, amount, currency, getCurrentTimestamp())
            viewModel.sendPayment(payment)
        }
    )

}
