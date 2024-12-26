package com.example.pennywise

import android.app.Application
import com.example.pennywise.database.TransactionRoomDatabase
import com.example.pennywise.repository.TransactionRepository

class PennywiseApplication : Application() {

    private lateinit var _database: TransactionRoomDatabase
    private lateinit var _repository: TransactionRepository

    override fun onCreate() {
        super.onCreate()
        _database = TransactionRoomDatabase.getDatabase(this)
        _repository = TransactionRepository(_database.transactionDAO())
    }

    val database: TransactionRoomDatabase
        get() = _database

    val repository: TransactionRepository
        get() = _repository
}
