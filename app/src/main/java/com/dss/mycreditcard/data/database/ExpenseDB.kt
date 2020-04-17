package com.dss.mycreditcard.data.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dss.mycreditcard.data.dao.ExpenseDao
import com.dss.mycreditcard.data.dao.SessionDao
import com.dss.mycreditcard.data.dao.SettingsDao
import com.dss.mycreditcard.data.entities.Expense
import com.dss.mycreditcard.data.entities.Session
import com.dss.mycreditcard.data.entities.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities =
[
    Expense::class,
    Settings::class,
    Session::class
], version = 2, exportSchema = false)
abstract class ExpenseDB : RoomDatabase()
{
    abstract fun expenseDao(): ExpenseDao
    abstract fun settingsDao(): SettingsDao
    abstract fun sessionDao(): SessionDao

    companion object
    {
        const val DB_NAME = "expenseDB"
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
                    ExpenseDB::class.java, DB_NAME)
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
                    populateDb()
                }
            }
        }

        private fun populateDb()
        {
            //stub
        }
    }
}