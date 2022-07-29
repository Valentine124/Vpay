package com.valentine.vpay.ui.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.valentine.vpay.R
import com.valentine.vpay.internet.TransactionsData

class TransactionsAdapters: ListAdapter<TransactionsData, TransactionsAdapters.TransactionsViewHolder>(TransactionComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        return TransactionsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.phoneNumber, current.type, current.amount, current.created)
    }

    class TransactionsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val account = itemView.findViewById<TextView>(R.id.account_transaction)
        private val type = itemView.findViewById<TextView>(R.id.transaction_type)
        private val amount = itemView.findViewById<TextView>(R.id.transaction_amount)
        private val date = itemView.findViewById<TextView>(R.id.date)

        fun bind(tran_account: String, tran_type: String, tran_amount: Double, tran_date: String) {
            account.text = tran_account
            type.text = tran_type
            amount.text = tran_amount.toString()
            date.text = tran_date
        }

        companion object {
            fun create(parent: ViewGroup): TransactionsViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.transactions_list_item, parent, false)
                return TransactionsViewHolder(view)
            }
        }
    }
}

class TransactionComparator: DiffUtil.ItemCallback<TransactionsData>() {
    override fun areItemsTheSame(oldItem: TransactionsData, newItem: TransactionsData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TransactionsData, newItem: TransactionsData): Boolean {
        return oldItem == newItem
    }
}