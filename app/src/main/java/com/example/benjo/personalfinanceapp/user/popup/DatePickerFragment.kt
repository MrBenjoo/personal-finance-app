package com.example.benjo.personalfinanceapp.user.popup

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.util.Log
import java.util.*

class DatePickerFragment : DialogFragment() {

    companion object {
        private var mListener: OnDateSetListener? = null
        private var mYear: Int = 0
        private var mMonth: Int = 0
        private var mDay: Int = 0

        fun newInstance(calendar: Calendar, listener: OnDateSetListener): DatePickerFragment {
            mListener = listener
            mYear = calendar.get(Calendar.YEAR)
            mMonth = calendar.get(Calendar.MONTH)
            mDay = calendar.get(Calendar.DAY_OF_MONTH)
            return DatePickerFragment()
        }

        fun unregisterListener() {
            mListener = null
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true
        return DatePickerDialog(activity, mListener, mYear, mMonth, mDay)
    }

    override fun onDestroyView() {
        if (dialog != null && retainInstance) {
            dialog.setDismissMessage(null)
        }
        super.onDestroyView()
    }

    fun setOnDateSetListener(listener: OnDateSetListener) {
        mListener = listener
    }

}