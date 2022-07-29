package com.valentine.vpay.ui.fund

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valentine.vpay.data.VpayRepository
import com.valentine.vpay.db.History
import com.valentine.vpay.internet.Transfer
import com.valentine.vpay.internet.TransferAndWithdrawBody

class FundViewModel(private val repository: VpayRepository): ViewModel() {

    suspend fun doTransfer(body: TransferAndWithdrawBody): Transfer {
        return repository.transfer(body)
    }

    suspend fun addHistory(history: History) {
        repository.addHistory(history)
    }
}

class FundFactory(private val repository: VpayRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FundViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FundViewModel(repository) as T
        }
        throw IllegalArgumentException("Class not found")
    }
}