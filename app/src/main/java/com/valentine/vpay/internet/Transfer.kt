package com.valentine.vpay.internet

data class Transfer(
    val `data`: TransferData,
    val message: String,
    val status: String
)