package com.valentine.vpay.internet

data class TransactionsData(
    val amount: Double,
    val balance: Double,
    val created: String,
    val phoneNumber: String,
    val type: String
)