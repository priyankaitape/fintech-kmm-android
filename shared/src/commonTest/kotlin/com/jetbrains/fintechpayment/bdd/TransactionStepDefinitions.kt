package com.jetbrains.fintechpayment.bdd


import com.jetbrains.fintechpayment.data.repository.PaymentRepositoryImpl
import com.jetbrains.fintechpayment.model.Payment
import com.jetbrains.fintechpayment.domain.usecase.GetPaymentsUseCase
import com.jetbrains.fintechpayment.datasource.local.FakePaymentLocalDataSource
import com.jetbrains.fintechpayment.datasource.remote.FakePaymentRemoteDataSource
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlin.test.assertTrue

class TransactionStepDefinitions {

    private lateinit var useCase: GetPaymentsUseCase
    private lateinit var fakeDataSource: FakePaymentLocalDataSource

    private var result: List<Payment>? = null

    @Given("transactions exist in the Firestore")
    fun setupFakeTransactions() {
        fakeDataSource = FakePaymentLocalDataSource().apply {
            seedSampleTransactions()
        }

        val fakeRemote = FakePaymentRemoteDataSource()
        val repo = PaymentRepositoryImpl(remoteDataSource = fakeRemote, localDataSource = fakeDataSource)

        useCase = GetPaymentsUseCase(repo)
    }

    @When("the user opens the transaction history screen")
    fun fetchTransactionHistory() = runBlocking {
        result = useCase().firstOrNull()
    }

    @Then("the user should see a list of past transactions")
    fun assertTransactionListNotEmpty() {
        assertTrue(result != null && result!!.isNotEmpty(), "Transaction list should not be empty")
    }
}
