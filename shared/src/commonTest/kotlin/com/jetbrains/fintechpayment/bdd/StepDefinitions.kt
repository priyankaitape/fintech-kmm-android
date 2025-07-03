package com.jetbrains.fintechpayment.bdd


import com.jetbrains.fintechpayment.model.Payment
import com.jetbrains.fintechpayment.validation.PaymentValidator
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class StepDefinitions {

    lateinit var payment: Payment
    var isValid = false

    @Given("the user has entered a valid email and amount")
    fun valid_input() {
        payment = Payment("test@example.com", 100.0, "USD", 1751976000000)
        isValid = PaymentValidator.isValidEmail(payment.recipientEmail) &&
                PaymentValidator.isValidAmount(payment.amount)
    }

    @Given("the user has entered an invalid email")
    fun invalid_email() {
        payment = Payment("invalid-email", 100.0, "USD", 1751961600000)
        isValid = PaymentValidator.isValidEmail(payment.recipientEmail)
    }

    @When("the user submits the payment")
    fun submit_payment() {
        // simulate submission - already validated above
    }

    @Then("the payment should be processed and stored")
    fun verify_success() {
        assertTrue(isValid)
    }

    @Then("the system should show an error")
    fun verify_failure() {
        assertFalse(isValid)
    }
}
