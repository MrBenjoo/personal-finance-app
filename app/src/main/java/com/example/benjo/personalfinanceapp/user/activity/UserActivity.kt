package com.example.benjo.personalfinanceapp.user.activity

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.example.benjo.personalfinanceapp.R
import com.example.benjo.personalfinanceapp.login.authentication.Constants
import com.example.benjo.personalfinanceapp.login.authentication.User
import com.example.benjo.personalfinanceapp.user.summary.SummaryFragment
import com.example.benjo.personalfinanceapp.user.list.ExpenditureFragment
import com.example.benjo.personalfinanceapp.user.transaction.ExpenditureTransaction
import com.example.benjo.personalfinanceapp.user.list.IncomeFragment
import com.example.benjo.personalfinanceapp.user.transaction.IncomeTransaction
import com.example.benjo.personalfinanceapp.utils.ContainerFragment
import com.borax12.materialdaterangepicker.date.*
import com.example.benjo.personalfinanceapp.user.UserPresenter
import com.example.benjo.personalfinanceapp.user.model.DateFromTo
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.activity_user_to_include.*
import kotlinx.android.synthetic.main.fragment_summary.*
import java.util.*
import kotlinx.android.synthetic.main.user_nav_header.view.*


class UserActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private var presenter: UserPresenter? = null
    val username get() = intent.getStringExtra(Constants.USER)!!

    companion object {
        fun newIntent(context: Context, user: User): Intent {
            val intent = Intent(context, UserActivity::class.java)
            intent.putExtra(Constants.USER, user.username)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        user_nav_view
                .getHeaderView(0)
                .textView
                .text = username
        presenter = UserPresenter(this, fragments(savedInstanceState))
        val navListener = NavigationListener(this)
        user_nav_view.setNavigationItemSelectedListener(navListener)
        user_bottom_nav.setOnNavigationItemSelectedListener(navListener)
    }


    private fun fragments(savedInstanceState: Bundle?): HashMap<String, Fragment> {
        val container = fragmentManager.findFragmentById(R.id.user_container_fragment) as ContainerFragment?
        val fragmentManager = container!!.childFragmentManager
        val fragments = if (savedInstanceState == null) createFragments(container) else fragmentsFromFM(fragmentManager)
        addFragmentsToContainer(container, fragments)
        fragments!!.put(Constants.CONTAINER, container)
        return fragments
    }

    private fun createFragments(container: ContainerFragment): HashMap<String, Fragment>? {
        val fragments = HashMap<String, Fragment>()
        with(fragments) {
            put(Constants.SUMMARY, SummaryFragment())
            put(Constants.INCOME, IncomeFragment())
            put(Constants.EXPENDITURE, ExpenditureFragment())
            put(Constants.INCOME_TRANSACTION, IncomeTransaction())
            put(Constants.EXPENDITURE_TRANSACTION, ExpenditureTransaction())
            container.currentTag = Constants.SUMMARY
            return this
        }
    }

    private fun fragmentsFromFM(fm: FragmentManager): HashMap<String, Fragment>? {
        val fragments = HashMap<String, Fragment>()
        with(fragments) {
            with(Constants) {
                put(SUMMARY, fm.findFragmentByTag(SUMMARY) as SummaryFragment)
                put(INCOME, fm.findFragmentByTag(INCOME) as IncomeFragment)
                put(EXPENDITURE, fm.findFragmentByTag(EXPENDITURE) as ExpenditureFragment)
                put(INCOME_TRANSACTION, fm.findFragmentByTag(INCOME_TRANSACTION) as IncomeTransaction)
                put(EXPENDITURE_TRANSACTION, fm.findFragmentByTag(EXPENDITURE_TRANSACTION) as ExpenditureTransaction)
            }
            return this
        }
    }

    private fun addFragmentsToContainer(container: ContainerFragment, fragments: HashMap<String, Fragment>?) {
        with(container) {
            with(fragments!!) {
                with(Constants) {
                    add(get(SUMMARY)!!, SUMMARY)
                    add(get(INCOME)!!, INCOME)
                    add(get(EXPENDITURE)!!, EXPENDITURE)
                    add(get(INCOME_TRANSACTION)!!, INCOME_TRANSACTION)
                    add(get(EXPENDITURE_TRANSACTION)!!, EXPENDITURE_TRANSACTION)
                }
            }
        }
    }

    fun showDateDialog() {
        val now = Calendar.getInstance()
        val dialog = DatePickerDialog.newInstance(this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show(fragmentManager, Constants.MULTIPLE_DATE_PICKER)
    }

    override fun onResume() {
        super.onResume()
        (fragmentManager.findFragmentByTag(Constants.MULTIPLE_DATE_PICKER) as? DatePickerDialog)?.setOnDateSetListener(this)
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int, yearEnd: Int, monthOfYearEnd: Int, dayOfMonthEnd: Int) {
        presenter!!.onDateSpanSet(DateFromTo(year, monthOfYear, dayOfMonth, yearEnd, monthOfYearEnd, dayOfMonthEnd))
    }

    fun showText(text: String) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG).show()
    }

    fun presenter(): UserPresenter? {
        return presenter
    }


}
