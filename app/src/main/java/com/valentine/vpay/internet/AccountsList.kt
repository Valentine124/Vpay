package com.valentine.vpay.internet

data class AccountsList(
    val `data`: List<AccountListData>,
    val message: String,
    val status: String
)