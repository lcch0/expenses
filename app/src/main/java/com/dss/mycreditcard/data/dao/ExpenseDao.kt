package com.dss.mycreditcard.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dss.mycreditcard.data.entities.Expense

@Dao
interface ExpenseDao
{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(expense: Expense): Long

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("delete from expenses")
    suspend fun deleteAll()

    @Query("select * from expenses where date between :startDate and :endDate order by date desc")
    fun getExpensesForPeriodAsync(startDate: Long, endDate: Long): LiveData<List<Expense>>

    @Query("select * from expenses where date between :startDate and :endDate order by date desc")
    fun getExpensesForPeriod(startDate: Long, endDate: Long): List<Expense>

    @Query("select * from expenses order by date desc")
    fun getAllExpensesAsync(): LiveData<MutableList<Expense>>

    @Query("select * from expenses order by date desc")
    fun getAllExpenses(): List<Expense>

    @Query("select sum(amount) from expenses where date between :startDate and :endDate")
    fun getTotalForPeriodAsync(startDate: Long, endDate: Long): LiveData<Float>
}