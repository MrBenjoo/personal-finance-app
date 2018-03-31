package com.example.benjo.personalfinanceapp.user.list


import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.example.benjo.personalfinanceapp.login.authentication.Constants
import com.example.benjo.personalfinanceapp.user.database.AnkoDB
import kotlinx.android.synthetic.main.transactions.*


class ExpenditureFragment : BaseFragment() {

    override fun attachTouchHelper(recyclerView: RecyclerView?) {
        val touchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                presenter!!.deleteTransaction(AnkoDB.TABLE_EXPENDITURE, viewHolder!!.itemView.tag as Int)
            }
        }
        ItemTouchHelper(touchCallback).attachToRecyclerView(recyclerView)
    }

    override fun onResume() {
        super.onResume()
        transactions_fab.setOnClickListener { presenter?.show(Constants.EXPENDITURE_TRANSACTION) }
        presenter!!.loadAllTransactions(AnkoDB.TABLE_EXPENDITURE)
    }

    override fun onRefresh() {
        presenter?.loadAllTransactions(AnkoDB.TABLE_EXPENDITURE)
        refresh?.isRefreshing = false
    }

    override fun onLongClick(view: View, position: Int) {
        TODO("No dialog implemented")
    }

}