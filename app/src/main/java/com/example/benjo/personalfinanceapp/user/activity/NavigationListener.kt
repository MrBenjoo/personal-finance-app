package com.example.benjo.personalfinanceapp.user.activity

import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.view.MenuItem
import android.view.animation.DecelerateInterpolator
import com.example.benjo.personalfinanceapp.R
import com.example.benjo.personalfinanceapp.login.authentication.Constants
import com.example.benjo.personalfinanceapp.user.UserPresenter
import com.example.benjo.personalfinanceapp.user.database.AnkoDB
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.activity_user_to_include.*
import kotlinx.android.synthetic.main.transactions.*

class NavigationListener(private val activity: UserActivity) : NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener {
    private var presenter: UserPresenter? = null

    init {
        presenter = activity.presenter()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.nav_summary ->
            activity.user_drawer_layout.consume {
                presenter?.show(Constants.SUMMARY)
                activity.user_bottom_nav.selectedItemId = R.id.nav_bottom_sum
            }
        R.id.nav_income ->
            activity.user_drawer_layout.consume {
                presenter?.show(Constants.INCOME)
                activity.user_bottom_nav.selectedItemId = R.id.nav_bottom_inc
            }
        R.id.nav_expenditure ->
            activity.user_drawer_layout.consume {
                presenter?.show(Constants.EXPENDITURE)
                activity.user_bottom_nav.selectedItemId = R.id.nav_bottom_exp
            }
        R.id.nav_bottom_sum ->
            activity.user_bottom_nav.consume {
                presenter?.show(Constants.SUMMARY)
                activity.user_nav_view.setCheckedItem(R.id.nav_summary)
            }
        R.id.nav_bottom_inc ->
            activity.user_bottom_nav.consume {
                presenter?.show(Constants.INCOME)
                activity.user_nav_view.setCheckedItem(R.id.nav_income)
            }
        R.id.nav_bottom_exp ->
            activity.user_bottom_nav.consume {
                presenter?.show(Constants.EXPENDITURE)
                activity.user_nav_view.setCheckedItem(R.id.nav_expenditure)
            }
        R.id.nav_time_span ->
            activity.user_drawer_layout.consume {
                activity.showDateDialog()
            }
        R.id.nav_clear_income ->
            activity.user_drawer_layout.consume {
                presenter?.onClearList(AnkoDB.TABLE_INCOME)
            }
        R.id.nav_clear_expenditure ->
            activity.user_drawer_layout.consume {
                presenter?.onClearList(AnkoDB.TABLE_EXPENDITURE)
            }
        else -> false
    }

    private inline fun DrawerLayout.consume(f: () -> Unit): Boolean {
        f()
        closeDrawers()
        return true
    }

    private inline fun BottomNavigationView.consume(f: () -> Unit): Boolean {
        f()
        animate()
                .translationY(0f)
                .interpolator = DecelerateInterpolator(2f)
        activity.transactions_fab
                .animate()
                .translationY(0f)
                .interpolator = DecelerateInterpolator(2f)
        return true
    }

}