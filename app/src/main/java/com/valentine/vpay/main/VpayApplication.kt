package com.valentine.vpay.main

import android.app.Application
import com.valentine.vpay.data.VpayRepository
import com.valentine.vpay.db.HistoryDatabase
import com.valentine.vpay.internet.veegilapi.VeegilApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VpayApplication: Application() {
    private val historyDatabase by lazy {
        HistoryDatabase.getDatabase(this)
    }
    val repository: VpayRepository by lazy {
        VpayRepository(VeegilApi.veegilService, historyDatabase.historyDao())
    }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            historyDatabase.historyDao().deleteAll()
        }
    }

}