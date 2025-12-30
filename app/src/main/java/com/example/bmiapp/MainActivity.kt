package com.example.bmiapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var calculateButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var categoryTextView: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize views
        weightEditText = findViewById(R.id.weightEditText)
        heightEditText = findViewById(R.id.heightEditText)
        calculateButton = findViewById(R.id.calculateButton)
        resultTextView = findViewById(R.id.resultTextView)
        categoryTextView = findViewById(R.id.categoryTextView)
        
        // Set click listener for calculate button
        calculateButton.setOnClickListener {
            handleInput()
        }
    }
    
    private fun handleInput() {
        // Read input values from EditText fields
        val weightText = weightEditText.text.toString().trim()
        val heightText = heightEditText.text.toString().trim()
        
        // Show Toast if inputs are empty
        if (weightText.isEmpty() || heightText.isEmpty()) {
            Toast.makeText(this, "Please enter both weight and height", Toast.LENGTH_SHORT).show()
            resultTextView.text = ""
            categoryTextView.text = ""
            return
        }
        
        // Convert input values to Double safely
        try {
            val weight = weightText.toDouble()
            val height = heightText.toDouble()
            
            // Prevent zero or negative values
            if (weight <= 0 || height <= 0) {
                Toast.makeText(this, "Please enter positive values greater than zero", Toast.LENGTH_SHORT).show()
                resultTextView.text = ""
                categoryTextView.text = ""
                return
            }
            
            // Input handling successful - calculate BMI
            calculateBMI(weight, height)
            
        } catch (e: NumberFormatException) {
            // Prevent app crash from invalid number format
            Toast.makeText(this, "Invalid number format. Please enter valid numbers", Toast.LENGTH_SHORT).show()
            resultTextView.text = ""
            categoryTextView.text = ""
        } catch (e: Exception) {
            // Catch any other unexpected errors to prevent crash
            Toast.makeText(this, "An error occurred. Please try again", Toast.LENGTH_SHORT).show()
            resultTextView.text = ""
            categoryTextView.text = ""
        }
    }
    
    private fun calculateBMI(weight: Double, height: Double) {
        // Convert height from cm to meters
        val heightInMeters = height / 100.0
        
        // Calculate BMI using formula: BMI = weight / (height * height)
        val bmi = weight / (heightInMeters * heightInMeters)
        
        // Round result to 2 decimal places
        val bmiRounded = String.format("%.2f", bmi)
        
        // Get BMI category
        val category = getBMICategory(bmi)
        
        // Display the result with formatted text
        resultTextView.text = "Your BMI: $bmiRounded"
        categoryTextView.text = "Category: $category"
    }
    
    private fun getBMICategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 25 -> "Normal"
            bmi < 30 -> "Overweight"
            else -> "Obese"
        }
    }
}

