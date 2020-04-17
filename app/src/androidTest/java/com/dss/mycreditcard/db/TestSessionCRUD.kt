package com.dss.mycreditcard.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dss.mycreditcard.data.database.ExpenseDB
import com.dss.mycreditcard.data.entities.PeriodType
import com.dss.mycreditcard.data.entities.Session
import com.dss.mycreditcard.data.entities.Settings
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class TestSessionCRUD
{
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val defaultSession = Session(0)
    private val calendar = GregorianCalendar()
    private val date2019m01d10 = calendar.apply { set(2019, 1, 10) }.time.time
    private val date2020m01b11 = calendar.apply {set(2020, 1, 11) }.time.time

    @After
    fun tearDown()
    {
        runBlocking {
            val dao =
                ExpenseDB.getDatabase(appContext, this).sessionDao()
            dao.delete()
        }
    }

    @Test
    fun insertTest()
    {
        runBlocking {
            val dao =
                ExpenseDB.getDatabase(appContext, this).sessionDao()

            val longRes = dao.set(defaultSession)
            assertTrue("Nothing is inserted", longRes > 0)
        }
    }

    @Test
    fun updateTest()
    {
        runBlocking {
            val dao =
                ExpenseDB.getDatabase(appContext, this).sessionDao()

            val id = dao.set(defaultSession).toInt()
            val settings = Session(id,
                date1 = date2019m01d10,
                date2 = date2020m01b11,
                amount = 5.5f,
                softLimit = 10f,
                hardLimit = 20f)

            dao.update(settings)

            val res = dao.get()

            assertTrue("Nothing is updated", res != null)

            res?.let {
                assertTrue("Nothing is updated",
                    it.date1 == date2019m01d10
                            && it.date2 == date2020m01b11
                            && it.amount == 5.5f
                            && it.softLimit == 10f
                            && it.hardLimit == 20f)
            }

        }
    }

    @Test
    fun deleteExpenseTest()
    {
        runBlocking {
            val dao =
                ExpenseDB.getDatabase(appContext, this).sessionDao()

            dao.set(defaultSession).toInt()
            dao.delete()
            val settings = dao.get()

            assertTrue("Nothing is updated", settings == null)
        }
    }
}