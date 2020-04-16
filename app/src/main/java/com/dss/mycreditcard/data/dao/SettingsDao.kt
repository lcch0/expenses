package com.dss.mycreditcard.data.dao

import androidx.room.*
import com.dss.mycreditcard.data.entities.Settings

@Dao
interface SettingsDao
{
    @Query("select * from settings limit 1")
    fun getSettings(): Settings

    @Insert
    fun setSettings(settings: Settings)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: Settings): Long

    @Update
    suspend fun update(settings: Settings)

    @Query("delete from settings")
    suspend fun deleteAll()
}