package com.dss.mycreditcard.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dss.mycreditcard.data.dao.ExpenseDe
import com.dss.mycreditcard.data.entities.Expense
import java.util.*

class ExpenseRepo(private val expenseDe: ExpenseDe)
{
    private var start = Date()
    private var end = Date()
    private var _expenses: LiveData<List<Expense>> = MutableLiveData<List<Expense>>()

    fun setPeriod(start: Date, end: Date)
    {
        this.start = start
        this.end = end
        _expenses = expenseDe.getExpensesForPeriodDesc(start.getTime(), end.getTime())
    }

    var expenses: LiveData<List<Expense>>
        get() = _expenses
        private set(value) {_expenses = value}

    suspend fun insert(expense: Expense)
    {
        expenseDe.insert(expense)
    }
}