package com.example.benjo.personalfinanceapp.user.list


import android.app.Fragment
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.example.benjo.personalfinanceapp.R
import com.example.benjo.personalfinanceapp.user.activity.UserActivity
import com.example.benjo.personalfinanceapp.user.UserPresenter
import com.example.benjo.personalfinanceapp.user.model.Transaction
import kotlinx.android.synthetic.main.activity_user_to_include.*
import kotlinx.android.synthetic.main.transactions.*
import kotlin.collections.ArrayList


abstract class BaseFragment : Fragment(), IRecyclerViewClickListener {
    var listAdapter: ListAdapter? = null
    var presenter: UserPresenter? = null
    var refresh: SwipeRefreshLayout? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.transactions, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initRefresh()
    }

    private fun initList() {
        listAdapter = ListAdapter(ArrayList<Transaction>(), this)
        with(transactions_rc) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity.applicationContext)
            adapter = listAdapter
            attachScrollListener(this)
            attachTouchHelper(this)
        }
    }

    private fun attachScrollListener(recyclerView: RecyclerView?) {
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                onScroll(dx, dy)
            }
        })
    }

    private fun onScroll(dx: Int, dy: Int) {
        val bottomNav = activity.user_bottom_nav
        val fab = transactions_fab
        val onDownScroll = dy > 0 && bottomNav.isShown
        when (onDownScroll) {
            true -> {
                val fabBottomMargin = (fab.layoutParams as ConstraintLayout.LayoutParams).bottomMargin
                bottomNav.animate()
                        .translationY(bottomNav.height.toFloat())
                        .interpolator = AccelerateInterpolator(2f)
                fab.animate()
                        .translationY(fab.height.toFloat() + fabBottomMargin)
                        .interpolator = AccelerateInterpolator(3f)
            }
            false -> {
                bottomNav.animate()
                        .translationY(0f)
                        .interpolator = DecelerateInterpolator(2f)
                fab.animate()
                        .translationY(0f)
                        .interpolator = DecelerateInterpolator(2f)
            }
        }
    }

    abstract fun attachTouchHelper(recyclerView: RecyclerView?)


    private fun initRefresh() {
        refresh = transactions_refresh
        refresh?.setOnRefreshListener { onRefresh() }
    }


    abstract override fun onLongClick(view: View, position: Int)

    override fun onResume() {
        super.onResume()
        presenter = (activity as UserActivity).presenter()
    }

    fun setList(transactions: ArrayList<Transaction>) {
        listAdapter?.setList(transactions)
    }

    fun addTransaction(transaction: Transaction) {
        listAdapter!!.addTransaction(transaction)
    }

    abstract fun onRefresh()

}