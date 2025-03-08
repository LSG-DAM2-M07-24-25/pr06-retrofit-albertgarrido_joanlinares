package com.example.pr06_retrofit_albertgarrido_joanlinares.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pr06_retrofit_albertgarrido_joanlinares.model.SearchHistoryEntry

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history ORDER BY id DESC")
    fun getSearchHistory(): List<SearchHistoryEntry>

    @Insert
    fun insertSearchEntry(entry: SearchHistoryEntry)

    @Query("DELETE FROM search_history")
    fun clearSearchHistory()
}
