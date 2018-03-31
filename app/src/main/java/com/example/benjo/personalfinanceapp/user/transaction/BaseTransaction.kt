package com.example.benjo.personalfinanceapp.user.transaction

import android.app.DatePickerDialog.OnDateSetListener
import android.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.example.benjo.personalfinanceapp.R
import com.example.benjo.personalfinanceapp.login.authentication.Constants
import com.example.benjo.personalfinanceapp.login.authentication.User
import com.example.benjo.personalfinanceapp.user.popup.DatePickerFragment
import com.example.benjo.personalfinanceapp.user.activity.UserActivity
import com.example.benjo.personalfinanceapp.user.UserPresenter
import com.example.benjo.personalfinanceapp.user.model.Transaction
import kotlinx.android.synthetic.main.transaction.*
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseTransaction : Fragment(), OnDateSetListener {
    var presenter: UserPresenter? = null
    var transaction: Transaction? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.transaction, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transaction_btn_categories.setOnClickListener { showDialogCategories() }
        transaction_btn_date.setOnClickListener { showDatePicker() }
        with(transaction_title.text.toString()) {
            transaction = Transaction(this, this, this, this, 0)
        }
        CustomTextWatch(transaction_title, transaction_amount, activity.applicationContext)
    }


    fun onTransaction() {
        with(transaction!!) {
            title = transaction_title.text.toString()
            amount = transaction_amount.text.toString()
        }
        Log.d("BaseTransaction", "onTransaction " + transaction!!.date)
    }

    private fun showDatePicker() {
        var picker: DatePickerFragment? = activity.fragmentManager.findFragmentByTag(Constants.DATE_PICKER) as? DatePickerFragment
        if (picker == null) {
            picker = DatePickerFragment.newInstance(Calendar.getInstance(), this)
        }
        picker.show(activity.fragmentManager, Constants.DATE_PICKER)
    }

    override fun onDestroy() {
        super.onDestroy()
        //DatePickerFragment.unregisterListener()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        transaction!!.date = dateFormat.format(calendar.time)
        if(transaction!!.date.isEmpty()) {
            Log.d("BaseTransaction", "no date set")
        } else {
            Log.d("BaseTransaction", "onDateSet " + transaction!!.date)
        }

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        with(savedInstanceState) {
            if (this != null) {
                if (containsKey("date")) {
                    transaction!!.date = getString("date")
                }
                if (containsKey("category")) {
                    transaction!!.category = getString("category")
                    transaction!!.imgRes = getInt("imgRes")
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        with(outState) {
            with(transaction!!) {
                Log.d("BaseTransaction", "onSaveInstanceState")
                if (date.isNotEmpty()) {
                    Log.d("BaseTransaction", "onSaveInstanceState " + date)
                    outState!!.putString("date", date)
                }
                if (category.isNotEmpty()) {
                    outState!!.putString("category", category)
                    outState.putInt("imgRes", imgRes)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //(activity.fragmentManager.findFragmentByTag(Constants.DATE_PICKER) as? DatePickerFragment)?.setOnDateSetListener(this)
        presenter = (activity as UserActivity).presenter()
    }

    abstract fun showDialogCategories()
}