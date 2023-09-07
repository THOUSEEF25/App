package com.example.app.activity

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.databinding.ActivityEditUserBinding
import com.example.app.model.User
import com.google.firebase.firestore.FirebaseFirestore

class EditUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditUserBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var editUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        // Retrieve the user data passed from the previous activity
        editUser = intent.getSerializableExtra("editUser") as User

        // Populate the EditText fields with user's data
        binding.name.setText(editUser.name)
        binding.email.setText(editUser.email)
        binding.phone.setText(editUser.phone)
        binding.address.setText(editUser.address)
        binding.planTo.setText(editUser.planTo)
        binding.followUpDateEditText.setText(editUser.followUpDate)
        binding.description.setText(editUser.description)

        binding.update.setOnClickListener {
            updateData()
        }
    }

    private fun updateData() {
        // Get the edited data from EditText fields
        val name = binding.name.text.toString()
        val email = binding.email.text.toString()
        val phone = binding.phone.text.toString()
        val address = binding.address.text.toString()
        val planTo = binding.planTo.text.toString()
        val followUpDateEditText = binding.followUpDateEditText.text.toString()
        val description = binding.description.text.toString()

        // Update the user document in Firestore
        val userRef = db.collection("users").document(editUser.documentId) // Use the correct document ID field
        val updatedUser = User(name, email, phone, address, followUpDateEditText, planTo, description)

        userRef.set(updatedUser)
            .addOnSuccessListener {
                Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error updating user", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Error updating user", exception)
            }
    }
}