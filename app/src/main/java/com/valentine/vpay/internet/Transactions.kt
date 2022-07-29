package com.valentine.vpay.internet

data class Transactions(
    val `data`: List<TransactionsData>,
    val status: String
)