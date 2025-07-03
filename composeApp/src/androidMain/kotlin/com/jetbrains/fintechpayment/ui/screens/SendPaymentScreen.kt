package com.jetbrains.fintechpayment.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.jetbrains.fintechpayment.R
import com.jetbrains.fintechpayment.validation.PaymentValidator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendPaymentScreen(
    modifier: Modifier = Modifier,
    onSendClick: (recipientEmail: String, amount: Double, currency: String) -> Unit
) {
    var recipientEmail by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }
    var selectedCurrency by remember { mutableStateOf("USD") }

    val currencies = listOf("USD","SDG", "EUR", "INR", "GBP") //Todo - get this list from Api or other data source
    var expanded by remember { mutableStateOf(false) }

    val emailValid = PaymentValidator.isValidEmail(recipientEmail)
    val amount = amountText.toDoubleOrNull()
    val amountValid = amount != null && PaymentValidator.isValidAmount(amount)
    val currencyValid = PaymentValidator.isSupportedCurrency(selectedCurrency)

    val isFormValid = emailValid && amountValid && currencyValid

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Email
        OutlinedTextField(
            value = recipientEmail,
            onValueChange = { recipientEmail = it },
            label = { Text(stringResource(id = R.string.recipient_email)) },
            singleLine = true,
            isError = !emailValid && recipientEmail.isNotBlank(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        if (!emailValid && recipientEmail.isNotBlank()) {
            Text(
                text = stringResource(id=R.string.recipient_email_error),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Amount
        OutlinedTextField(
            value = amountText,
            onValueChange = { amountText = it },
            label = { Text(stringResource(id=R.string.amount)) },
            singleLine = true,
            isError = !amountValid && amountText.isNotBlank(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        if (!amountValid && amountText.isNotBlank()) {
            Text(
                text = stringResource(id=R.string.amount_error),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Currency dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedCurrency,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.currency)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                currencies.forEach { currency ->
                    DropdownMenuItem(
                        text = { Text(currency) },
                        onClick = {
                            selectedCurrency = currency
                            expanded = false
                        }
                    )
                }
            }
        }
        if (!currencyValid) {
            Text(
                text = stringResource(id=R.string.currency_error),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Submit button
        Button(
            onClick = {
                if (amount != null) {
                    onSendClick(recipientEmail.trim(), amount, selectedCurrency)

                }
            },
            enabled = isFormValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(stringResource(id=R.string.send))
        }
    }
}
