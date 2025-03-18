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
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {

    private lateinit var fullNameText: TextView
    private lateinit var ageText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var emailText: TextView
    private lateinit var editProfileButton: Button
    private val db = FirebaseFirestore.getInstance()

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
            val userId = currentUser.uid

            db.collection("users").document(userId)
                .addSnapshotListener { document, error ->
                    if (error != null) {
                        Toast.makeText(activity, "Failed to load user data.", Toast.LENGTH_SHORT).show()
                        return@addSnapshotListener
                    }

                    if (document != null && document.exists()) {
                        val user = document.toObject(User::class.java)
                        user?.let {
                            fullNameText.text = it.fullName
                            ageText.text = it.age
                            descriptionText.text = it.description
                        }
                    }
                }
        }

        editProfileButton.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            val editProfileFragment = EditProfileFragment()
            fragmentTransaction.replace(R.id.fragment_container, editProfileFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return rootView
    }
}
