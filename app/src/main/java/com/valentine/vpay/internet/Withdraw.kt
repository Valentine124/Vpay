package com.valentine.vpay.internet

data class Withdraw(
    val `data`: WithdrawData,
    val message: String,
    val status: String
)