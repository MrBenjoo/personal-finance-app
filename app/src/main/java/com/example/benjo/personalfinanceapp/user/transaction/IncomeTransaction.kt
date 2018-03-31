package com.example.benjo.personalfinanceapp.user.transaction


import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.benjo.personalfinanceapp.R
import com.example.benjo.personalfinanceapp.login.authentication.Constants
import com.example.benjo.personalfinanceapp.user.popup.DialogCategories
import com.example.benjo.personalfinanceapp.user.database.AnkoDB
import kotlinx.android.synthetic.main.transaction.*


class IncomeTransaction : BaseTransaction(), DialogCategories.DialogCategoriesListener {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transaction_btn_add.setOnClickListener { transaction() }
    }

    fun transaction() {
        super.onTransaction()
        Log.d("IncomeTransaction", "date: " + transaction?.date + " category: " + transaction?.category)
        presenter?.onTransaction(transaction!!, AnkoDB.TABLE_INCOME)
    }


    override fun showDialogCategories() {
        var dialogCategories: DialogCategories? = fragmentManager.findFragmentByTag(Constants.DIALOG_INCOME) as? DialogCategories
        if (dialogCategories == null) {
            dialogCategories = DialogCategories.newInstance(Constants.INCOME_CATEGORIES)
        }
        dialogCategories.setTargetFragment(this, 300)
        dialogCategories.show(fragmentManager, Constants.DIALOG_INCOME)
    }

    override fun onFinishEditDialog(category: String) {
        with(transaction!!) {
            this.category = category
            when (category) {
                "Salary" -> imgRes = R.drawable.ic_salary
                "Other" -> imgRes = R.drawable.ic_other
            }
        }
    }

}