package com.dss.mycreditcard.data.dao

import androidx.room.*
import com.dss.mycreditcard.data.entities.Settings

@Dao
interface SettingsDao
{
    @Query("select * from settings limit 1")
    fun get(): Settings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set(settings: Settings): Long

    @Update
    suspend fun update(settings: Settings)

    @Query("delete from settings")
    suspend fun delete()
}