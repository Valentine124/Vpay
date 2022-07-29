package com.valentine.vpay.ui.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valentine.vpay.data.VpayRepository
import com.valentine.vpay.db.History
import com.valentine.vpay.internet.Transfer
import com.valentine.vpay.internet.TransferAndWithdrawBody
import com.valentine.vpay.internet.Withdraw

class TransferViewModel(private val repository: VpayRepository): ViewModel() {
    suspend fun doTransfer(body: TransferAndWithdrawBody): Transfer {
        return repository.transfer(body)
    }

    suspend fun addHistory(history: History) {
        repository.addHistory(history)
    }

    suspend fun doWithdraw(body: TransferAndWithdrawBody): Withdraw {
        return repository.withdraw(body)
    }
}

class TransferFactory(private val repository: VpayRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransferViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransferViewModel(repository) as T
        }
        throw IllegalArgumentException("Class not found")
    }
}