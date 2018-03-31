package com.example.benjo.personalfinanceapp.user

import android.app.Fragment
import com.example.benjo.personalfinanceapp.login.authentication.Constants
import com.example.benjo.personalfinanceapp.user.activity.UserActivity
import com.example.benjo.personalfinanceapp.user.list.ExpenditureFragment
import com.example.benjo.personalfinanceapp.user.list.IncomeFragment
import com.example.benjo.personalfinanceapp.user.summary.SummaryFragment
import com.example.benjo.personalfinanceapp.user.model.Transaction
import com.example.benjo.personalfinanceapp.user.database.AnkoDB
import com.example.benjo.personalfinanceapp.user.model.DateFromTo
import com.example.benjo.personalfinanceapp.user.transaction.ExpenditureTransaction
import com.example.benjo.personalfinanceapp.user.transaction.IncomeTransaction
import com.example.benjo.personalfinanceapp.utils.ContainerFragment
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UserPresenter(activity: UserActivity, fragments: HashMap<String, Fragment>) {
    private var userActivity: UserActivity? = null
    private var summary: SummaryFragment? = null
    private var income: IncomeFragment? = null
    private var expenditure: ExpenditureFragment? = null
    private var incTransaction: IncomeTransaction? = null
    private var expTransaction: ExpenditureTransaction? = null
    private var container: ContainerFragment? = null
    private var database: AnkoDB? = null

    init {
        userActivity = activity
        with(fragments) {
            with(Constants) {
                summary = get(SUMMARY) as SummaryFragment
                income = get(INCOME) as IncomeFragment
                expenditure = get(EXPENDITURE) as ExpenditureFragment
                incTransaction = get(INCOME_TRANSACTION) as IncomeTransaction
                expTransaction = get(EXPENDITURE_TRANSACTION) as ExpenditureTransaction
                container = get(CONTAINER) as ContainerFragment
            }
        }
        container?.setAnimation(android.R.animator.fade_in, android.R.animator.fade_out)
        database = AnkoDB(activity.applicationContext, activity.username)
    }

    fun onTransaction(transaction: Transaction, table: String) {
        if (transaction.isValid()) {
            completable { database!!.addTransaction(transaction, table) }
                    .subscribe { addTransactionToList(table) }
        } else {
            showText("Invalid transaction. Fill in all the fields.")
        }
    }

    private fun addTransactionToList(table: String) {
        observable { database!!.getLatestTransaction(table) }
                .subscribe({ transaction: Transaction? ->
                    when (table) {
                        AnkoDB.TABLE_INCOME -> {
                            income!!.addTransaction(transaction!!)
                            show(Constants.INCOME)
                        }
                        AnkoDB.TABLE_EXPENDITURE -> {
                            expenditure!!.addTransaction(transaction!!)
                            show(Constants.EXPENDITURE)
                        }
                    }
                    showText("Transaction added")
                })
    }

    fun loadAllTransactions(table: String) {
        observable { database!!.getTransactions(table) }
                .subscribe({ transactions: ArrayList<Transaction>? ->
                    when (table) {
                        AnkoDB.TABLE_INCOME -> income!!.setList(transactions!!)
                        AnkoDB.TABLE_EXPENDITURE -> expenditure!!.setList(transactions!!)
                    }
                })
    }

    fun onClearList(table: String) {
        completable { database!!.clearTable(table) }
                .subscribe {
                    when (table) {
                        AnkoDB.TABLE_INCOME ->
                            income!!.setList(ArrayList<Transaction>())
                        AnkoDB.TABLE_EXPENDITURE ->
                            expenditure!!.setList(ArrayList<Transaction>())
                    }
                }
    }

    fun deleteTransaction(table: String, row: Int) {
        observable { database!!.deleteTransaction(table, row) }
                .subscribe { deleted: Boolean ->
                    if (deleted) {
                        when (table) {
                            AnkoDB.TABLE_INCOME -> {
                                loadAllTransactions(AnkoDB.TABLE_INCOME)
                            }
                            AnkoDB.TABLE_EXPENDITURE -> {
                                loadAllTransactions(AnkoDB.TABLE_EXPENDITURE)
                            }
                        }
                    } else showText("Unable to delete transaction.")
                }
    }

    fun onDateSpanSet(dateFromTo: DateFromTo) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        with(dateFromTo) {
            cal1.set(year, monthOfYear, dayOfMonth)
            cal2.set(yearEnd, monthOfYearEnd, dayOfMonthEnd)
        }
        val dateFrom = dateFormat.format(cal1.time)
        val dateTo = dateFormat.format(cal2.time)
        Observable.fromCallable { database!!.getTransactionsFromTo(dateFrom, dateTo) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { transactionsFromTo: ArrayList<Transaction>? ->
                    expenditure!!.setList(transactionsFromTo!!)
                    income!!.setList(transactionsFromTo)
                    showText(dateFrom + " - " + dateTo)
                }
    }


    fun show(tag: String) {
        container?.show(tag)
    }

    fun showText(text: String) {
        userActivity!!.showText(text)
    }

    private fun <T> observable(function: () -> T): Observable<T> {
        return Observable.fromCallable(function)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun completable(function: () -> Unit): Completable {
        return Completable.fromAction(function)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}