package com.jetbrains.fintechpayment.state

sealed class UiState<out T> {
    object Idle : UiState<Nothing>() // initial state
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
