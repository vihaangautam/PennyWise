package com.example.pennywise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.google.android.material.textfield.TextInputEditText
import android.widget.Toast

class NewTransactionActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var amount: TextInputEditText
    private lateinit var spinner: Spinner
    private lateinit var add: Button
    private var type: String = ""
    private var money: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_transaction)

        initialize()

        add.setOnClickListener {
            val replyIntent = Intent()
            // Check if the amount field is empty or invalid
            val amountText = amount.text.toString()
            if (TextUtils.isEmpty(amountText) || amountText.toIntOrNull() == null || amountText.toInt() <= 0) {
                // Show an error message if validation fails
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                setResult(RESULT_CANCELED, replyIntent)
            } else {
                // Set the values to the intent if everything is valid
                money = amountText.toInt()
                replyIntent.putExtra("Amount", money)
                replyIntent.putExtra("Type", type)
                setResult(RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    private fun initialize() {
        amount = findViewById(R.id.amount_input)
        add = findViewById(R.id.add_button)
        spinner = findViewById(R.id.dropdown_menu)

        // Setting up the spinner with options
        ArrayAdapter.createFromResource(
            this,
            R.array.spinner_options,
            android.R.layout.simple_spinner_dropdown_item // Use the default layout
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Use default dropdown item layout
            spinner.adapter = adapter
        }

        // Setting up the item selected listener
        spinner.onItemSelectedListener = this
    }

    // Called when an item is selected from the spinner
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        type = parent?.getItemAtPosition(position) as String
    }

    // Called when nothing is selected in the spinner
    override fun onNothingSelected(parent: AdapterView<*>?) {
        // You can handle this if necessary, though it's usually not needed
    }
}
