package com.example.pennywise

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pennywise.viewmodel.TransactionViewModel
import com.example.pennywise.viewmodel.TransactionViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize ViewModel
        val repository = (application as PennywiseApplication).repository
        transactionViewModel = ViewModelProvider(
            this,
            TransactionViewModelFactory(repository)
        )[TransactionViewModel::class.java]

        // Initialize adapter with ViewModel
        val adapter = RecyclerViewAdapter(transactionViewModel)
        recyclerView.adapter = adapter

        // Observe transactions
        transactionViewModel.allTransactions.observe(this) { transactions ->
            transactions?.let { adapter.submitList(it) }
        }

        // FAB for new transaction
        findViewById<FloatingActionButton>(R.id.fabAddTransaction).setOnClickListener {
            startActivity(Intent(this, NewTransactionActivity::class.java))
        }
    }
}
