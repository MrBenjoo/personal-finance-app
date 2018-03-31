package com.example.benjo.personalfinanceapp.user.transaction

import android.content.Context
import android.support.design.widget.TextInputEditText
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import com.example.benjo.personalfinanceapp.R


class CustomTextWatch(private val title: TextInputEditText,
                      private val amount: TextInputEditText,
                      private val context: Context) : TextWatcher {

    init {
        title.addTextChangedListener(this)
        amount.addTextChangedListener(this)
    }


    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { /* Empty */
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { /* Empty */
    }

    override fun afterTextChanged(editable: Editable?) {
        when (editable!!.hashCode()) {
            title.text.hashCode() -> setError(title, "Write a title")
            else -> setError(amount, "Write an amount")
        }
    }

    private fun setError(et: TextInputEditText, errorMsg: String) {
        with(title) {
            if (et == this && text.isNotEmpty() && text[0].isWhitespace()) {
                error = "White space not allowed as first character"
                return
            }
        }
        if (et.text.isEmpty())
            et.error = errorMsg
        else
            et.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.ic_check_green_18dp), null)
    }

}
