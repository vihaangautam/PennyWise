package com.example.pennywise.viewmodel

import androidx.lifecycle.*
import com.example.pennywise.model.Transaction
import com.example.pennywise.repository.TransactionRepository
import kotlinx.coroutines.launch

class TransactionViewModel(private val repository: TransactionRepository) : ViewModel() {
    val allTransactions: LiveData<List<Transaction>> = repository.allTransactions.asLiveData()

    fun delete(transaction: Transaction) = viewModelScope.launch {
        repository.delete(transaction)
    }
}

class TransactionViewModelFactory(private val repository: TransactionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
