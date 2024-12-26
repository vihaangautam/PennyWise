package com.example.pennywise.repository

import com.example.pennywise.database.TransactionDAO
import com.example.pennywise.model.Transaction
import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val transactionDAO: TransactionDAO) {

    val allTransactions: Flow<List<Transaction>> = transactionDAO.getAllTransactions()

    suspend fun insert(transaction: Transaction) {
        transactionDAO.insert(transaction)
    }

    suspend fun delete(transaction: Transaction) {
        transactionDAO.delete(transaction)
    }

    suspend fun deleteAll() {
        transactionDAO.deleteAll()
    }

    fun getTotalSpent(): Flow<Int> {
        return transactionDAO.getTotalSpent()
    }

    fun getTodaySpent(time: Long): Flow<Int> {
        return transactionDAO.getTodaySpent(time)
    }
}
