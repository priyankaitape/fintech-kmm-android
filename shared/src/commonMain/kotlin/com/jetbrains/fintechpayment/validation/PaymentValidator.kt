package com.jetbrains.fintechpayment.validation

object PaymentValidator {
    fun isValidEmail(email: String): Boolean =
        email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
    fun isValidAmount(amount: Double): Boolean = amount > 0
    fun isSupportedCurrency(currency: String): Boolean = currency in listOf("SDG", "USD", "EUR","INR","GBP")
}