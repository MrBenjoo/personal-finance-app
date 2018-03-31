package com.example.benjo.personalfinanceapp.user.list


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.personalfinanceapp.R
import com.example.benjo.personalfinanceapp.user.model.Transaction
import kotlinx.android.synthetic.main.item_row_list.view.*


class ListAdapter(transactions: ArrayList<Transaction>, private val listener: IRecyclerViewClickListener) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var transactions: ArrayList<Transaction>? = null

    init {
        this.transactions = transactions
    }

    fun setList(transactions: ArrayList<Transaction>) {
        this.transactions?.clear()
        this.transactions?.addAll(transactions)
        notifyDataSetChanged()
    }

    fun addTransaction(transaction: Transaction) {
        transactions!!.add(transaction)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_row_list, parent, false)
        return ViewHolder(view, listener);
    }

    override fun getItemCount(): Int {
        return transactions?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        transactions?.let {
            with(holder) {
                title.text = it[position].title
                amount.text = it[position].amount
                category.text = it[position].category
                date.text = it[position].date
                imgRes.setImageResource(it[position].imgRes)
                itemView.tag = (adapterPosition)
            }
        }
    }

    class ViewHolder(itemView: View, listener: IRecyclerViewClickListener?) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.row_title
        val amount = itemView.row_amount
        val category = itemView.row_category
        val date = itemView.row_date
        val imgRes = itemView.row_img

        init {
            itemView.setOnLongClickListener {
                listener!!.onLongClick(it, adapterPosition)
                true
            }
        }
    }

}