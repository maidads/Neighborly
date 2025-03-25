package com.example.myneighborly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddHelpFragment : Fragment() {

    private lateinit var helpCategory: EditText
    private lateinit var address: EditText
    private lateinit var date: EditText
    private lateinit var details: EditText
    private lateinit var addHelpButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_add_help, container, false)

        helpCategory = rootView.findViewById(R.id.helpCategory)
        address = rootView.findViewById(R.id.address)
        date = rootView.findViewById(R.id.date)
        details = rootView.findViewById(R.id.details)
        addHelpButton = rootView.findViewById(R.id.addHelpButton)

        addHelpButton.setOnClickListener {
            saveHelpRequest()
        }

        return rootView
    }

    private fun saveHelpRequest() {
        val category = helpCategory.text.toString().trim()
        val addressText = address.text.toString().trim()
        val dateText = date.text.toString().trim()
        val detailsText = details.text.toString().trim()
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        if (category.isEmpty() || addressText.isEmpty() || dateText.isEmpty() || detailsText.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val helpRequest = HelpRequest(
            id = "",
            category = category,
            address = addressText,
            date = dateText,
            details = detailsText,
            userId = userId
        )

        val db = FirebaseFirestore.getInstance()
        val helpRef = db.collection("helpRequests").document()

        helpRequest.id = helpRef.id

        helpRef.set(helpRequest)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Help request added!", Toast.LENGTH_SHORT).show()
                //findNavController().popBackStack()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to add request", Toast.LENGTH_SHORT).show()
            }
    }
}
