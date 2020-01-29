package com.dss.mycreditcard.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dss.mycreditcard.R
import com.dss.mycreditcard.data.entities.Expense
import com.dss.mycreditcard.viewModels.ExpenseViewModel
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    private lateinit var viewModel: ExpenseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)
        viewModel.expenses.observe(viewLifecycleOwner, Observer {
            populateModel(it)
        })
    }

    private fun populateModel(expenses: List<Expense>?) {
        if (expenses == null) {
            amountText.text = 0.toString()
            return
        }

        var amount = 0f
        for (e in expenses) {
            amount += e.amount
        }

        amountText.text = amount.toString()
    }
}
