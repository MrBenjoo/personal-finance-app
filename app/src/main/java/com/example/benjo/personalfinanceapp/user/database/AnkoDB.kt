package com.example.benjo.personalfinanceapp.user.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.benjo.personalfinanceapp.login.authentication.Constants
import com.example.benjo.personalfinanceapp.user.model.Transaction
import org.jetbrains.anko.db.*

class AnkoDB(context: Context, username : String) : ManagedSQLiteOpenHelper(context, username + ".db", null, 2) {


    fun addTransaction(transaction: Transaction, TABLE: String) {
        use {
            with(Constants) {
                insert(TABLE,
                        COLUMN_ITEM_INDEX to getSize(TABLE),
                        COLUMN_TITLE to transaction.title,
                        COLUMN_CATEGORY to transaction.category,
                        COLUMN_AMOUNT to transaction.amount,
                        COLUMN_DATE to transaction.date,
                        COLUMN_IMG to transaction.imgRes)
            }
        }
    }

    fun getTransactions(table: String): ArrayList<Transaction> {
        val transactions = ArrayList<Transaction>()
        use {
            select(table).parseList(object : MapRowParser<List<Transaction>> {
                override fun parseRow(columns: Map<String, Any?>): List<Transaction> {
                    transactions.add(getTransaction(columns))
                    return transactions
                }
            })
        }
        return transactions
    }

    fun deleteTransaction(table: String, position: Int): Boolean {
        var deleted = false
        use {
            select(table)
            deleted = delete(table, Constants.COLUMN_ITEM_INDEX + "=?", arrayOf(position.toString())) > 0
            if (deleted) {
                execSQL("UPDATE " + table +
                        " SET " + Constants.COLUMN_ITEM_INDEX + " = " + Constants.COLUMN_ITEM_INDEX + " -1 " +
                        " WHERE " + Constants.COLUMN_ITEM_INDEX + " > " + position + ";")

                /*with(Constants) {
                    update(table, COLUMN_ITEM_INDEX to (COLUMN_ITEM_INDEX + (-1)))
                            .whereSimple(COLUMN_ITEM_INDEX + " > ?", position.toString())
                            .exec()*/
                }
            }
        return deleted
    }

    fun getLatestTransaction(table: String): Transaction? {
        var transaction: Transaction? = null
        use {
            select(table)
                    .orderBy(Constants.COLUMN_ID, (SqlOrderDirection.DESC))
                    .limit(1)
                    .parseOpt(object : MapRowParser<Transaction> {
                        override fun parseRow(columns: Map<String, Any?>): Transaction {
                            transaction = getTransaction(columns)
                            return transaction!!
                        }
                    })
        }
        return transaction
    }


    fun getTransactionsFromTo(dateFrom: String, dateTo: String): ArrayList<Transaction> {
        val transactions = ArrayList<Transaction>()
        use {
            select(TABLE_INCOME)
                    .whereSimple(Constants.COLUMN_DATE + " between ? and ?", dateFrom, dateTo).parseList(object : MapRowParser<List<Transaction>> {
                override fun parseRow(columns: Map<String, Any?>): List<Transaction> {
                    transactions.add(getTransaction(columns))
                    return transactions
                }
            })
        }
        return transactions
    }

    companion object {
        const val TABLE_INCOME: String = "TableIncome"
        const val TABLE_EXPENDITURE: String = "TableExpenditure"

        // Anropas aldrig --> behövs egentligen inte
        // private var instance: AnkoDB? = null
        /*@Synchronized
        fun getInstance(ctx: Context): AnkoDB {
            if (instance == null) {
                instance = AnkoDB(ctx.applicationContext)
            }
            return instance!!
        }*/

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Constants.DATABASE_CREATE(TABLE_INCOME))
        db.execSQL(Constants.DATABASE_CREATE(TABLE_EXPENDITURE))
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }


    private fun getSize(table: String): Int {
        var mSize = 0
        use {
            select(table).parseList(object : RowParser<Any> {
                override fun parseRow(columns: Array<Any?>): Any {
                    return mSize++
                }
            })
        }
        return mSize
    }


    private fun getTransaction(columns: Map<String, Any?>): Transaction {
        with(Constants) {
            val title = columns[COLUMN_TITLE].toString()
            val amount = columns[COLUMN_AMOUNT].toString()
            val date = columns[COLUMN_DATE].toString()
            val category = columns[COLUMN_CATEGORY].toString()
            val img = columns[COLUMN_IMG].toString().toInt()
            return Transaction(title, amount, category, date, img)
        }
    }


    fun clearTable(table: String) {
        use {
            dropTable(table, true)
            execSQL(Constants.DATABASE_CREATE(table))
        }
    }

}

// Anropas aldrig --> behövs egentligen inte
/*val Context.database: AnkoDB
    get() = AnkoDB.getInstance(applicationContext)*/