package com.example.pennywise

import android.app.Application
import com.example.pennywise.database.TransactionRoomDatabase

class TransactionApplication : Application() {
    val database by lazy { TransactionRoomDatabase.getDatabase(this) }
}
