package com.jetbrains.fintechpayment.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.graphics.vector.ImageVector
import com.jetbrains.fintechpayment.R

sealed class BottomNavScreen(
    val route: String,
    val title: Int,
    val icon: ImageVector
) {
    object SendPayment : BottomNavScreen("send_payment", R.string.send_payment , Icons.Filled.Send)
    object History : BottomNavScreen("transaction_history", R.string.transactions, Icons.Filled.List)

    companion object {
        val items = listOf(SendPayment, History)
    }
}
