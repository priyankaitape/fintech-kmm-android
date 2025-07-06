package com.jetbrains.fintechpayment.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jetbrains.fintechpayment.R
import com.jetbrains.fintechpayment.model.Payment
import com.jetbrains.fintechpayment.viewmodel.PaymentViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionHistoryScreen(viewModel: PaymentViewModel = koinViewModel()) {
    val payments by viewModel.transactions.collectAsState(initial = emptyList())

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (payments.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id=R.string.empty_list),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(payments) { payment ->
                    TransactionItem(payment)
                }
            }
        }
    }
}

@Composable
fun TransactionItem(payment: Payment) {
    val formattedDate = try {
        val sdf = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
        sdf.format(Date(payment.timestamp))
    } catch (e: Exception) {
        payment.timestamp // fallback if parsing fails
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = MaterialTheme.shapes.medium

//        colors = CardDefaults.cardColors( //customize background color
//            containerColor = White
//        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(stringResource(id= R.string.recipient) + ": ${payment.recipientEmail}", style = MaterialTheme.typography.bodyMedium)
            Text(stringResource(id= R.string.amount) + ": ${payment.amount} ${payment.currency}", style = MaterialTheme.typography.bodyMedium)
            Text(stringResource(id= R.string.date) + ": $formattedDate", style = MaterialTheme.typography.labelSmall)
        }
    }
}
