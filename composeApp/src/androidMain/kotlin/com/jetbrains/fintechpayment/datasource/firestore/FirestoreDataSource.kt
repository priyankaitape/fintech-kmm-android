package com.jetbrains.fintechpayment.datasource.firestore


import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jetbrains.fintechpayment.data.local.PaymentLocalDataSource
import com.jetbrains.fintechpayment.model.Payment
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirestoreDataSource : PaymentLocalDataSource {

    private val db = Firebase.firestore  //FirebaseFirestore.getInstance()

    override fun savePayment(payment: Payment, onResult: (Boolean) -> Unit) {
        db.collection("transactions")
            .add(payment)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    // Fetch real-time updates of all payments
    override fun getPaymentsFlow(): Flow<List<Payment>> = callbackFlow {
        val listener = db.collection("transactions")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // Log the error and emit empty list
                    android.util.Log.e("Firestore", "Snapshot listener error", error)
                    trySend(emptyList()).isSuccess
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val payments =
                        snapshot.documents.mapNotNull { it.toObject(Payment::class.java) }
                    trySend(payments).isSuccess
                }
            }
        awaitClose { listener.remove() }
    }
}
