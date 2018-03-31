package com.example.benjo.personalfinanceapp.user.transaction


import android.os.Bundle
import android.view.View
import com.example.benjo.personalfinanceapp.R
import com.example.benjo.personalfinanceapp.login.authentication.Constants
import com.example.benjo.personalfinanceapp.user.popup.DialogCategories
import com.example.benjo.personalfinanceapp.user.database.AnkoDB
import kotlinx.android.synthetic.main.transaction.*


class ExpenditureTransaction : BaseTransaction(), DialogCategories.DialogCategoriesListener {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transaction_btn_add.setOnClickListener { transaction() }
    }

    fun transaction() {
        super.onTransaction()
        presenter?.onTransaction(transaction!!, AnkoDB.TABLE_EXPENDITURE)
    }

    override fun showDialogCategories() {
        var dialogCategories: DialogCategories? = fragmentManager.findFragmentByTag(Constants.DIALOG_EXPENDITURE) as? DialogCategories
        if (dialogCategories == null) {
            dialogCategories = DialogCategories.newInstance(Constants.EXPENDITURE_CATEGORIES)
        }
        dialogCategories.setTargetFragment(this, 300)
        dialogCategories.show(fragmentManager, Constants.DIALOG_EXPENDITURE)
    }

    override fun onFinishEditDialog(category: String) {
        with(transaction!!) {
            this.category = category
            when (category) {
                "Accommodation" -> imgRes = R.drawable.ic_accommodation
                "Leisure" -> imgRes = R.drawable.ic_leisure
                "Travel" -> imgRes = R.drawable.ic_travel
                "Food" -> imgRes = R.drawable.ic_food
                "Other" -> imgRes = R.drawable.ic_food
            }
        }
    }
}