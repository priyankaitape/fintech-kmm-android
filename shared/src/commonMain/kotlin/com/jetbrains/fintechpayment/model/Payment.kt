package com.jetbrains.fintechpayment.model

import kotlinx.serialization.Serializable

@Serializable
data class Payment(
    val recipientEmail: String = "",
    val amount: Double = 0.0,
    val currency: String = "USD",
    val timestamp: Long = System.currentTimeMillis()
)//Default values are important for Firebase deserialization.


