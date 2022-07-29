package com.valentine.vpay.internet.veegilapi

import com.valentine.vpay.authentication.UserAuth
import com.valentine.vpay.internet.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL = "https://bank.veegil.com/"

//private val moshi = Moshi.Builder()
    //.add(KotlinJsonAdapterFactory())
    //.build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL).build()

interface VeegilService {
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun logInUser(@Body body: UserAuth): LogIn

    @Headers("Content-Type: application/json")
    @POST("auth/signup")
    suspend fun signUp(@Body body: UserAuth): SignIn

    @Headers("Content-Type: application/json")
    @POST("accounts/transfer")
    suspend fun transfer(@Body body: TransferAndWithdrawBody): Transfer

    @Headers("Content-Type: application/json")
    @POST("accounts/withdraw")
    suspend fun withdraw(@Body body: TransferAndWithdrawBody): Withdraw

    @GET("accounts/list")
    suspend fun getAccounts(): AccountsList

    @GET("transactions")
    suspend fun getTransactions(): Transactions

}

object VeegilApi {
    val veegilService : VeegilService by lazy {
        retrofit.create(VeegilService::class.java)
    }
}