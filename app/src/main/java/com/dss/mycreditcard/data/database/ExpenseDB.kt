package com.dss.mycreditcard.data.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dss.mycreditcard.data.dao.ExpenseDe
import com.dss.mycreditcard.data.entities.Expense
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Expense::class], version = 1, exportSchema = false)
abstract class ExpenseDB : RoomDatabase()
{
    abstract fun expenseDao(): ExpenseDe

    companion object
    {
        @Volatile
        private var INSTANCE: ExpenseDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ExpenseDB
        {
            INSTANCE?.let {
                return it
            }

            synchronized(this){
                val i = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDB::class.java, "expenseDB")
                    .addCallback(ExpenseDBCallback(scope))
                    .build()
                INSTANCE = i
                return i
            }
        }
    }

    private class ExpenseDBCallback(private val scope: CoroutineScope) : RoomDatabase.Callback()
    {
        override fun onOpen(db: SupportSQLiteDatabase)
        {
            super.onOpen(db)
            INSTANCE?.let {
                scope.launch {
                    populateDb(it.expenseDao())
                }
            }
        }

        private suspend fun populateDb(expenseDao: ExpenseDe)
        {
            //for debug
            expenseDao.deleteAll()

            var expense = Expense(0, 10f, Date().time)
            expenseDao.insert(expense)
            expense = Expense(0, 20f, Date().time)
            expenseDao.insert(expense)
        }
    }
}