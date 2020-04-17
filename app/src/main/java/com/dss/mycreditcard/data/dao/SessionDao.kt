package com.dss.mycreditcard.data.dao

import androidx.room.*
import com.dss.mycreditcard.data.entities.Session

@Dao
interface SessionDao
{
    @Query("select * from sessions limit 1")
    fun get(): Session?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set(session: Session): Long

    @Update
    suspend fun update(session: Session)

    @Query("delete from sessions")
    suspend fun delete()
}