package com.example.myneighborly

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val fullNameEditText: EditText = findViewById(R.id.fullNameEditText)
        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val phoneEditText: EditText = findViewById(R.id.phoneEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val termsCheckBox: CheckBox = findViewById(R.id.termsCheckBox)
        val nextButton: Button = findViewById(R.id.nextButton)

        nextButton.setOnClickListener {
            Log.d("SignUpActivity", "Next button clicked")

            val fullName = fullNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val password = passwordEditText.text.toString()
            val agreedToTerms = termsCheckBox.isChecked

            if (fullName.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty() && agreedToTerms) {
                Log.d("SignUpActivity", "All fields are filled correctly.")
            } else {
                Log.d("SignUpActivity", "One or more fields are missing.")
            }
        }
    }
}
