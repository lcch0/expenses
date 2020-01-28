package com.dss.mycreditcard.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dss.mycreditcard.data.ExpenseRepo
import com.dss.mycreditcard.data.database.ExpenseDB
import com.dss.mycreditcard.data.entities.Expense
import kotlinx.coroutines.launch
import java.util.*

class ExpenseViewModel(application: Application) : AndroidViewModel(application)
{
    private val repo: ExpenseRepo
    val expenses: LiveData<List<Expense>>

    init
    {
        val dao = ExpenseDB.getDatabase(application, viewModelScope).expenseDao()
        repo = ExpenseRepo(dao)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        repo.setPeriod(calendar.time, Date())
        expenses = repo.expenses
    }

    fun insert(expense: Expense) = viewModelScope.launch {
        repo.insert(expense)
    }
}