package com.jetbrains.fintechpayment.data.remote

import android.util.Log
import com.jetbrains.fintechpayment.model.Payment
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class PaymentService : PaymentRemoteDataSource {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }
    }

    private val BASE_URL = "http://10.0.2.2:3000" // for Android Emulator

    override suspend fun sendPaymentToApi(payment: Payment): Boolean {
        return try {
            val response: HttpResponse = client.post("$BASE_URL/payments") {
                contentType(ContentType.Application.Json)
                setBody(payment)
            }
            response.status == HttpStatusCode.Created || response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            Log.e("FintechApp", "Error: ${e.message}", e)
            false
        }
    }
}
