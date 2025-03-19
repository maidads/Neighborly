package com.example.myneighborly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.database.FirebaseDatabase

class EditProfileFragment : Fragment() {

    private lateinit var editFullName: EditText
    private lateinit var editAge: EditText
    private lateinit var editDescription: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        editFullName = rootView.findViewById(R.id.editFullName)
        editAge = rootView.findViewById(R.id.editAge)
        editDescription = rootView.findViewById(R.id.editDescription)
        saveButton = rootView.findViewById(R.id.saveButton)

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            val db = FirebaseFirestore.getInstance()

            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val user = document.toObject(User::class.java)
                        user?.let {
                            editFullName.setText(it.fullName)
                            editAge.setText(it.age)
                            editDescription.setText(it.description)
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Failed to load profile data", Toast.LENGTH_SHORT).show()
                }
        }

        saveButton.setOnClickListener {
            val fullName = editFullName.text.toString()
            val age = editAge.text.toString()
            val description = editDescription.text.toString()

            if (currentUser != null) {
                val userId = currentUser.uid
                val db = FirebaseFirestore.getInstance()

                val userRef = db.collection("users").document(userId)

                val updatedUser = User(fullName, currentUser.email ?: "", age, description)

                userRef.set(updatedUser)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(activity, "Profile updated successfully", Toast.LENGTH_SHORT).show()

                            activity?.supportFragmentManager?.popBackStack()
                        } else {
                            Toast.makeText(activity, "Failed to update profile", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        return rootView
    }
}
