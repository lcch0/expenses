package com.dss.mycreditcard.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dss.mycreditcard.data.database.ExpenseDB
import com.dss.mycreditcard.data.entities.Expense
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import java.util.*


@RunWith(AndroidJUnit4::class)
class TestExpensesCRUD
{
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val calendar = GregorianCalendar()
    private val date2019m01d10 = calendar.apply { set(2019, 1, 10) }.time.time
    private val date2020m01b11 = calendar.apply {set(2020, 1, 11) }.time.time
    private val date2020m10d11 = calendar.apply {set(2020, 10, 11) }.time.time


    @After
    fun tearDown()
    {
        runBlocking {
            val dao =
                ExpenseDB.getDatabase(appContext, this).expenseDao()
            dao.deleteAll()
        }
    }

    @Test
    fun insertExpenseTest()
    {
        runBlocking {
            val dao =
                ExpenseDB.getDatabase(appContext, this).expenseDao()

            val expense = Expense(0, 10f, Date().time)

            val longRes = dao.insert(expense)
            assertTrue("Nothing is inserted", longRes > 0)
        }
    }

    @Test
    fun updateExpenseTest()
    {
        runBlocking {
            val dao =
                ExpenseDB.getDatabase(appContext, this).expenseDao()

            val date = Date().time
            var expense = Expense(0, 10f, date)
            val id = dao.insert(expense).toInt()

            expense = Expense(id, 20f, date)
            dao.update(expense)

            val res = dao.getAllExpenses()

            assertTrue("Nothing is updated",
                res.isNotEmpty() &&
                        res[0].amount == 20f &&
                        res[0].date == date)
        }
    }

    @Test
    fun deleteExpenseTest()
    {
        runBlocking {
            val dao =
                ExpenseDB.getDatabase(appContext, this).expenseDao()

            val date = Date().time
            val expense = Expense(0, 10f, date)
            val id = dao.insert(expense).toInt()

            dao.delete(Expense(id, 0f, 0))

            val res = dao.getAllExpenses()

            assertTrue("Nothing is updated", res.isEmpty())
        }
    }

    @Test
    fun getExpenseByPeriodTest()
    {
        runBlocking {
            val dao =
                ExpenseDB.getDatabase(appContext, this).expenseDao()

            val expense1 = Expense(0, 10f, date2019m01d10)
            dao.insert(expense1).toInt()

            val expense2 = Expense(0, 10f, date2020m01b11)
            dao.insert(expense2).toInt()

            val expense3 = Expense(0, 10f, date2020m10d11)
            dao.insert(expense3).toInt()

            val res = dao.getExpensesForPeriod(expense1.date, expense2.date)

            assertTrue("Nothing is updated",
                res.size == 2 &&
                        res[0].date == date2020m01b11 &&
                        res[1].date == date2019m01d10)
        }
    }

    @Test
    fun getAllExpenseTest()
    {
        runBlocking {
            val dao =
                ExpenseDB.getDatabase(appContext, this).expenseDao()

            val expense = Expense(0, 20f, Date().time)

            val longRes = dao.insert(expense).toInt()
            val res = dao.getAllExpenses()

            assertTrue("No data read",
                res.isNotEmpty()
                        && res[0].id == longRes
                        && res[0].amount == 20f)
        }
    }
}