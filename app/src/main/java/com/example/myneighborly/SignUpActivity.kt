package com.example.myneighborly

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        val fullNameEditText: EditText = findViewById(R.id.fullNameEditText)
        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val phoneEditText: EditText = findViewById(R.id.phoneEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val termsCheckBox: CheckBox = findViewById(R.id.termsCheckBox)
        val nextButton: Button = findViewById(R.id.nextButton)
        val logInLink: TextView = findViewById(R.id.textView2)

        nextButton.setOnClickListener {
            Log.d("SignUpActivity", "Next button clicked")

            val fullName = fullNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val password = passwordEditText.text.toString()
            val agreedToTerms = termsCheckBox.isChecked

            if (fullName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && agreedToTerms) {
                Log.d("SignUpActivity", "All fields are filled correctly.")

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            Log.d("SignUpActivity", "User registered successfully: ${user?.email}")
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Log.w("SignUpActivity", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Log.d("SignUpActivity", "One or more fields are missing.")
                Toast.makeText(this, "Please fill all fields and accept terms.", Toast.LENGTH_SHORT).show()
            }
        }

        logInLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
