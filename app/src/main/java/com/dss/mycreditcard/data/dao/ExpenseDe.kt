package com.dss.mycreditcard.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dss.mycreditcard.data.entities.Expense

@Dao
interface ExpenseDe
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: Expense): Long

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("delete from expenses")
    suspend fun deleteAll()

    @Query("select * from expenses where date between :startDate and :endDate order by date desc")
    fun getExpensesForPeriodDesc(startDate: Long, endDate: Long): LiveData<List<Expense>>

    @Query("select sum(amount) from expenses where date between :startDate and :endDate")
    fun getTotalForPeriodDesc(startDate: Long, endDate: Long): LiveData<Float>
}