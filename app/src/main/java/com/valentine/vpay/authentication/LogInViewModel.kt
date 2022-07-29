package com.valentine.vpay.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valentine.vpay.data.VpayRepository
import com.valentine.vpay.internet.LogIn

class LogInViewModel(private val repository: VpayRepository): ViewModel() {

    suspend fun logInUser(phoneNumber: String, password: String): LogIn? {
            return try {
                val obj = UserAuth(phoneNumber, password)
                Log.e("LogInViewModel", "Success")
                repository.logInUser(obj)
            } catch (e: Exception) {
                Log.e("LogInViewModel", e.message!!)
                null
            }
    }

}

class AuthFactory(private val repository: VpayRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogInViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LogInViewModel(repository) as T
        }
        throw IllegalArgumentException("Class Not Found")
    }
}