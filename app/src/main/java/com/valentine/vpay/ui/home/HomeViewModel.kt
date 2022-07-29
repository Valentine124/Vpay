package com.valentine.vpay.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.valentine.vpay.data.VpayRepository
import com.valentine.vpay.internet.AccountsList

class HomeViewModel(private val repository: VpayRepository): ViewModel() {

    val histories = repository.histories.asLiveData()

    suspend fun getAccounts(): AccountsList {
        return repository.getAccounts()
    }
}

class HomeFactory(private val repository: VpayRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Class not found")
    }
}