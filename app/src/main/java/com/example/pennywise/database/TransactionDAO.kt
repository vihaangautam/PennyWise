package com.example.pennywise.database

import androidx.room.*
import com.example.pennywise.model.Transaction // Make sure this import is correct
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDAO {

    @Query("SELECT * FROM transaction_table")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("DELETE FROM transaction_table")
    suspend fun deleteAll()

    @Query("SELECT SUM(amount) FROM transaction_table")
    fun getTotalSpent(): Flow<Int?> // Use Int? to handle potential null sums

    @Query("SELECT SUM(amount) FROM transaction_table WHERE time >= :time")
    fun getTodaySpent(time: Long): Flow<Int?> // Use Int? to handle potential null sums

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)
}