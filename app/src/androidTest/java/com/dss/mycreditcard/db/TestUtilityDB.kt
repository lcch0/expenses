package com.dss.mycreditcard.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dss.mycreditcard.data.database.ExpenseDB
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File


@RunWith(AndroidJUnit4::class)
class TestUtilityDB
{
    private val delete = false
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    @Test
    fun deleteDB()
    {
        if(!delete)
            return

        val databases = File(appContext.getApplicationInfo().dataDir.toString() + "/databases")
        val db = File(databases, ExpenseDB.DB_NAME)
        if (db.delete()) println("Database deleted") else println("Failed to delete database")

        val journal = File(databases, ExpenseDB.DB_NAME + "-journal")
        if (journal.exists())
        {
            if (journal.delete()) println("Database journal deleted") else println("Failed to delete database journal")
        }
    }
}