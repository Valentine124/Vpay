package com.valentine.vpay.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history")
    fun getHistory(): Flow<List<History>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistory(history: History)

    @Query("DELETE FROM history")
    suspend fun deleteAll()
}