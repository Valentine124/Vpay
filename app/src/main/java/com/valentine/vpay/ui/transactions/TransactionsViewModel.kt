package com.valentine.vpay.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valentine.vpay.data.VpayRepository
import com.valentine.vpay.internet.Transactions

class TransactionsViewModel(private val repository: VpayRepository): ViewModel() {
    suspend fun getTransactions(): Transactions {
        return repository.getTransactions()
    }
}

class TransactionsFactory(private val repository: VpayRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionsViewModel(repository) as T
        }
        throw IllegalArgumentException("Class not found")
    }
}