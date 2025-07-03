package com.jetbrains.fintechpayment.validation

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PaymentValidatorTest {

    @Test
    fun `valid email should pass`() {
        assertTrue(PaymentValidator.isValidEmail("test@example.com"))
    }

    @Test
    fun `invalid email should fail`() {
        assertFalse(PaymentValidator.isValidEmail("invalid_email"))
    }

    @Test
    fun `amount greater than zero should pass`() {
        assertTrue(PaymentValidator.isValidAmount(50.0))
    }

    @Test
    fun `amount zero or negative should fail`() {
        assertFalse(PaymentValidator.isValidAmount(0.0))
        assertFalse(PaymentValidator.isValidAmount(-10.0))
    }

    @Test
    fun `supported currency should pass`() {
        assertTrue(PaymentValidator.isSupportedCurrency("USD"))
        assertTrue(PaymentValidator.isSupportedCurrency("EUR"))
    }

    @Test
    fun `unsupported currency should fail`() {
        assertFalse(PaymentValidator.isSupportedCurrency("INR"))
        assertFalse(PaymentValidator.isSupportedCurrency("GBP"))
    }
}
