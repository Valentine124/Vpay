package com.valentine.vpay.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.valentine.vpay.R
import com.valentine.vpay.db.History

class HistoryAdapter: ListAdapter<History, HistoryAdapter.HistoryViewHolder>(HistoryComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.phoneNumber, current.type, current.amount)
    }

    class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val phone = itemView.findViewById<TextView>(R.id.account_history)
        private val type = itemView.findViewById<TextView>(R.id.type)
        private val amount = itemView.findViewById<TextView>(R.id.history_amount)

        fun bind(phoneNum: String, typeHistory: String, amountHistory: Double) {
            phone.text = phoneNum
            type.text = typeHistory
            amount.text = amountHistory.toString()
        }

        companion object {
            fun create(parent: ViewGroup): HistoryViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)

                return HistoryViewHolder(view)
            }
        }
    }
}

class HistoryComparator: DiffUtil.ItemCallback<History>() {
    override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem.id == newItem.id
    }
}