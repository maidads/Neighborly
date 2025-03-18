package com.example.myneighborly

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    private lateinit var fullNameText: TextView
    private lateinit var ageText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var emailText: TextView
    private lateinit var editProfileButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        fullNameText = rootView.findViewById(R.id.fullNameText)
        ageText = rootView.findViewById(R.id.ageText)
        descriptionText = rootView.findViewById(R.id.descriptionText)
        editProfileButton = rootView.findViewById(R.id.editProfileButton)

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("users")
            val userId = currentUser.uid

            usersRef.child(userId).get().addOnSuccessListener { dataSnapshot ->
                val user = dataSnapshot.getValue(User::class.java)
                user?.let {
                    fullNameText.text = it.fullName
                    ageText.text = it.age
                    descriptionText.text = it.description
                }
            }.addOnFailureListener {
                Toast.makeText(activity, "Failed to load user data.", Toast.LENGTH_SHORT).show()
            }
        }

        editProfileButton.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }
}
