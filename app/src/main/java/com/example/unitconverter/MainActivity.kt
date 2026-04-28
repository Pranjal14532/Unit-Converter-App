package com.example.unitconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var etInput: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var btnConvert: MaterialButton
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        etInput = findViewById(R.id.etInput)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        btnConvert = findViewById(R.id.btnConvert)
        tvResult = findViewById(R.id.tvResult)

        // All categories
        val categories = arrayOf("Length", "Weight", "Temperature")

        // Attach categories to category spinner
        val categoryAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        spinnerCategory.adapter = categoryAdapter

        // Change units based on category selected
        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: android.view.View?,
                position: Int, id: Long
            ) {
                when (categories[position]) {

                    "Length" -> loadUnits(arrayOf("Meter", "Kilometer", "Centimeter", "Millimeter"))
                    "Weight" -> loadUnits(arrayOf("Gram", "Kilogram", "Pound"))
                    "Temperature" -> loadUnits(arrayOf("Celsius", "Fahrenheit", "Kelvin"))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btnConvert.setOnClickListener {
            convertUnits()
        }
    }

    // Set units in spinners
    private fun loadUnits(units: Array<String>) {
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, units)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter
    }

    // Conversion Function (update as needed)
    private fun convertUnits() {
        val inputText = etInput.text.toString()
        if (inputText.isEmpty()) {
            etInput.error = "Enter a value"
            return
        }

        val inputValue = inputText.toDouble()
        val category = spinnerCategory.selectedItem.toString()
        val from = spinnerFrom.selectedItem.toString()
        val to = spinnerTo.selectedItem.toString()

        var result = 0.0

        when (category) {

            "Length" -> {
                val meterValue = when (from) {
                    "Meter" -> inputValue
                    "Kilometer" -> inputValue * 1000
                    "Centimeter" -> inputValue / 100
                    "Millimeter" -> inputValue / 1000
                    else -> inputValue
                }
                result = when (to) {
                    "Meter" -> meterValue
                    "Kilometer" -> meterValue / 1000
                    "Centimeter" -> meterValue * 100
                    "Millimeter" -> meterValue * 1000
                    else -> meterValue
                }
            }

            "Weight" -> {
                val gramValue = when (from) {
                    "Gram" -> inputValue
                    "Kilogram" -> inputValue * 1000
                    "Pound" -> inputValue * 453.592
                    else -> inputValue
                }
                result = when (to) {
                    "Gram" -> gramValue
                    "Kilogram" -> gramValue / 1000
                    "Pound" -> gramValue / 453.592
                    else -> gramValue
                }
            }

            "Temperature" -> {
                result = when {
                    from == "Celsius" && to == "Fahrenheit" -> (inputValue * 9/5) + 32
                    from == "Celsius" && to == "Kelvin" -> inputValue + 273.15

                    from == "Fahrenheit" && to == "Celsius" -> (inputValue - 32) * 5/9
                    from == "Fahrenheit" && to == "Kelvin" -> (inputValue - 32) * 5/9 + 273.15

                    from == "Kelvin" && to == "Celsius" -> inputValue - 273.15
                    from == "Kelvin" && to == "Fahrenheit" -> (inputValue - 273.15) * 9/5 + 32

                    else -> inputValue
                }
            }
        }

        tvResult.text = "Result: $result $to"
    }
}
