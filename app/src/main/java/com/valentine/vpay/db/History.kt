package com.valentine.vpay.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "phone")
    val phoneNumber: String,
    @ColumnInfo(name = "transactionType")
    val type: String,
    @ColumnInfo(name = "amount")
    val amount: Double
)