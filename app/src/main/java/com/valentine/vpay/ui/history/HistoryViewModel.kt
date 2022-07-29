package com.valentine.vpay.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.valentine.vpay.data.VpayRepository

class HistoryViewModel(repository: VpayRepository): ViewModel() {

    val histories = repository.histories.asLiveData()
}

class HistoryFactory(private val repository: VpayRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Class not found")
    }
}