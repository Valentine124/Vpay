package com.valentine.vpay.internet

data class TransferAndWithdrawBody(
    val phoneNumber: String,
    val amount: Double
)