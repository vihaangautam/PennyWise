package com.example.pennywise

import android.app.AlertDialog
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pennywise.model.Transaction
import com.example.pennywise.viewmodel.TransactionViewModel
import com.example.pennywise.repository.TransactionRepository
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

class RecyclerViewAdapter(
    private val transactionViewModel: TransactionViewModel
) : ListAdapter<Transaction, RecyclerViewAdapter.TransactionViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false)
        return TransactionViewHolder(view, transactionViewModel)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TransactionViewHolder(
        itemView: View,
        private val viewModel: TransactionViewModel
    ) : RecyclerView.ViewHolder(itemView) {
        private val amount: TextView = itemView.findViewById(R.id.amount_type)
        private val time: TextView = itemView.findViewById(R.id.time)
        private val delete: Button = itemView.findViewById(R.id.delete)
        private val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)

        fun bind(transaction: Transaction?) {
            transaction?.let {
                amount.text = "${it.amount} Rs, ${it.type}"

                val format = SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault())
                val cal = Calendar.getInstance().apply { timeInMillis = it.time }
                time.text = format.format(cal.time)

                val color = when (it.type) {
                    "Food" -> ContextCompat.getColor(itemView.context, R.color.Food)
                    "Shopping" -> ContextCompat.getColor(itemView.context, R.color.Shopping)
                    "Utility Bill" -> ContextCompat.getColor(itemView.context, R.color.Utility_Bill)
                    "Rent" -> ContextCompat.getColor(itemView.context, R.color.Rent)
                    "Entertainment" -> ContextCompat.getColor(itemView.context, R.color.Entertainment)
                    else -> ContextCompat.getColor(itemView.context, R.color.Others)
                }
                linearLayout.setBackgroundColor(color)

                delete.setOnClickListener {
                    AlertDialog.Builder(itemView.context)
                        .setTitle("Delete Transaction?")
                        .setMessage("Are you sure you want to delete the transaction?")
                        .setPositiveButton("Yes") { _, _ ->
                            viewModel.delete(transaction)
                        }
                        .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
                        .create()
                        .show()
                }
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
                oldItem == newItem
        }
    }
}