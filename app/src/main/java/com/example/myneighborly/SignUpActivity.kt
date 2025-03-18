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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

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
                            val userId = user?.uid

                            val newUser = User(fullName, email, "- år", "")

                            if (userId != null) {
                                firestore.collection("users")
                                    .document(userId)
                                    .set(newUser)
                                    .addOnCompleteListener { dbTask ->
                                        if (dbTask.isSuccessful) {
                                            Log.d("SignUpActivity", "User data saved to Firestore")
                                        } else {
                                            Log.e("SignUpActivity", "Failed to save user data: ${dbTask.exception}")
                                        }
                                    }
                            }

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
