package com.valentine.vpay.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valentine.vpay.data.VpayRepository
import com.valentine.vpay.internet.SignIn

class SignUpViewModel(private val repository: VpayRepository): ViewModel() {

    suspend fun signUpUser(phoneNumber: String, password: String): SignIn? {
        return try {
            val obj = UserAuth(phoneNumber, password)
            Log.e("SignUpViewModel", "Success")
            repository.signUpUser(obj)
        } catch (e: Exception) {
            Log.e("SignUpViewModel", e.message!!)
            null
        }
    }
}

class SignUpFactory(private val repository: VpayRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignUpViewModel(repository) as T
        }
        throw IllegalArgumentException("Class Not Found")
    }
}