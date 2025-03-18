package com.example.myneighborly

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class EditProfileActivity : AppCompatActivity() {

    private lateinit var editFullName: EditText
    private lateinit var editAge: EditText
    private lateinit var editDescription: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        editFullName = findViewById(R.id.editFullName)
        editAge = findViewById(R.id.editAge)
        editDescription = findViewById(R.id.editDescription)
        saveButton = findViewById(R.id.saveButton)

        val currentUser = FirebaseAuth.getInstance().currentUser

        saveButton.setOnClickListener {
            val fullName = editFullName.text.toString()
            val age = editAge.text.toString()
            val description = editDescription.text.toString()

            if (currentUser != null) {
                val userId = currentUser.uid
                val database = FirebaseDatabase.getInstance()
                val usersRef = database.getReference("users")

                val updatedUser = User(fullName, currentUser.email ?: "", age, description)

                usersRef.child(userId).setValue(updatedUser)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}
