package com.valentine.vpay.data

import com.valentine.vpay.authentication.UserAuth
import com.valentine.vpay.db.History
import com.valentine.vpay.db.HistoryDao
import com.valentine.vpay.internet.*
import com.valentine.vpay.internet.veegilapi.VeegilService

class VpayRepository(private val service: VeegilService, private val historyDao: HistoryDao) {

    suspend fun logInUser(body: UserAuth): LogIn {
        return service.logInUser(body)
    }

    suspend fun signUpUser(body: UserAuth) : SignIn{
        return service.signUp(body)
    }

    suspend fun withdraw(body: TransferAndWithdrawBody): Withdraw {
        return service.withdraw(body)
    }

    suspend fun transfer(body: TransferAndWithdrawBody): Transfer {
        return service.transfer(body)
    }

    suspend fun getAccounts(): AccountsList {
        return service.getAccounts()
    }

    suspend fun getTransactions(): Transactions {
        return service.getTransactions()
    }

    //DataBase
    val histories = historyDao.getHistory()

    suspend fun addHistory(history: History) {
        historyDao.addHistory(history)
    }

    suspend fun deleteAllHistory() {
        historyDao.deleteAll()
    }

}