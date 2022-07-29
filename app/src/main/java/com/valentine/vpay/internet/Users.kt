package com.valentine.vpay.internet

data class Users(
    val `data`: List<AccountListData>,
    val message: String,
    val status: String
)