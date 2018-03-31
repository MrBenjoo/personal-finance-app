package com.example.benjo.personalfinanceapp.user.popup

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_NEGATIVE
import android.content.DialogInterface.BUTTON_POSITIVE
import android.os.Bundle
import com.example.benjo.personalfinanceapp.login.authentication.Constants


class DialogCategories : DialogFragment(), DialogInterface.OnClickListener {
    private var category: String? = null

    companion object {
        private val incCategories = arrayOf("Salary", "Other")
        private val expCategories = arrayOf("Accommodation", "Leisure", "Travel", "Food", "Other")
        private var categories: Array<String>? = null

        fun newInstance(categories: Int): DialogCategories {
            this.categories = if (categories == Constants.INCOME_CATEGORIES) incCategories else expCategories
            return DialogCategories()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
                .setTitle("Categories")
                .setSingleChoiceItems(categories, 0, this)
                .setPositiveButton("OK", this)
                .setNegativeButton("Cancel", this)
                .create()
    }

    override fun onClick(dialog: DialogInterface?, i: Int) {
        when (i) {
            BUTTON_POSITIVE -> category?.let {
                val listener = targetFragment as DialogCategoriesListener
                listener.onFinishEditDialog(it)
            }
            BUTTON_NEGATIVE -> dialog?.cancel()
            else -> category = categories?.get(i)
        }
    }

    interface DialogCategoriesListener {
        fun onFinishEditDialog(category: String)
    }
}