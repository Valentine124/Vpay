package com.valentine.vpay.ui.withdraw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valentine.vpay.data.VpayRepository
import com.valentine.vpay.db.History
import com.valentine.vpay.internet.AccountsList
import com.valentine.vpay.internet.TransferAndWithdrawBody

class WithdrawViewModel(private val repository: VpayRepository): ViewModel() {

    suspend fun addHistory(history: History) {
        repository.addHistory(history)
    }

    suspend fun doWithdraw(body: TransferAndWithdrawBody) {
        repository.withdraw(body)
    }
}

class WithdrawFactory(private val repository: VpayRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WithdrawViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WithdrawViewModel(repository) as T
        }
        throw IllegalArgumentException("Class not found")
    }
}