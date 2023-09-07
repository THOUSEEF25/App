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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        @Suppress("DEPRECATION")
        val editUser = intent.getSerializableExtra("editUser") as User

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

        // Retrieve updated data from EditText fields
        val name = binding.name.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val phone = binding.phone.text.toString().trim()
        val address = binding.address.text.toString().trim()
        val planTo = binding.planTo.text.toString().trim()
        val followUpDateEditText = binding.followUpDateEditText.text.toString().trim()
        val description = binding.description.text.toString().trim()

        // Create a map with the updated data
        val updatedData = mapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "address" to address,
            "planTo" to planTo,
            "followUpDate" to followUpDateEditText,
            "description" to description
        )

        // Update data in Firestore
        db.collection("users")
            .document()
            .update(updatedData)
            .addOnSuccessListener {
                // Data updated successfully
                Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { exception ->
                // Handle update failure
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Error updating user", exception)
            }
    }
}