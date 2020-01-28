package com.dss.mycreditcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dss.mycreditcard.data.entities.Expense
import com.dss.mycreditcard.viewModels.ExpenseViewModel
import kotlinx.android.synthetic.main.expenses_view.*

class MainActivity : AppCompatActivity()
{
    private lateinit var viewModel: ExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)
        viewModel.expenses.observe(this, Observer{
            populateModel(it)
        })
    }

    private fun populateModel(expenses: List<Expense>?)
    {
        if(expenses == null)
        {
            amountText.text = 0.toString()
            return
        }

        var amount = 0f
        for (e in expenses)
        {
            amount+= e.amount
        }

        amountText.text = amount.toString()
    }
}
