package com.example.app.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.app.R
import com.example.app.databinding.ActivityEditUserBinding
import com.example.app.model.User
import com.google.firebase.firestore.FirebaseFirestore

//class EditUserActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityEditUserBinding
//    private lateinit var db: FirebaseFirestore
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityEditUserBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        db = FirebaseFirestore.getInstance()
//
//        binding.update.setOnClickListener {
//            updateData()
//        }
//
//    }
//
//    private fun updateData() {
//
//        val editUser = intent.getSerializableExtra("editUser") as User
//
//        binding.name.setText(editUser.name)
//        binding.email.setText(editUser.email)
//        binding.phone.setText(editUser.phone)
//        binding.address.setText(editUser.address)
//        binding.planTo.setText(editUser.planTo)
//        binding.followUpDateEditText.setText(editUser.followUpDate)
//        binding.description.setText(editUser.description)
//
//        val updatedName = binding.name.text.toString()
//        val updatedEmail = binding.email.text.toString()
//        val phone = binding.phone.text.toString()
//        val address = binding.address.text.toString()
//        val planTo = binding.planTo.text.toString()
//        val followup = binding.followUpDateEditText.text.toString()
//        val description = binding.description.text.toString()
//
//        // Query Firestore to find the document based on the email
//        db.collection("users")
//            .whereEqualTo("phone", phone)
//            .get()
//            .addOnSuccessListener { querySnapshot ->
//                if (!querySnapshot.isEmpty) {
//                    // Assuming there's only one matching document (unique email)
//                    val documentSnapshot = querySnapshot.documents[0]
//                    val documentId = documentSnapshot.id
//
//                    // Update the document with the new values
//                    db.collection("users").document(documentId)
//                        .update(
//                            "name", updatedName,
//                            "email", updatedEmail,
//                            "phone", phone,
//                            "address", address,
//                            "planTo", planTo,
//                            "followUpDate", followup,
//                            "description", description
//                        )
//                        .addOnSuccessListener {
//                            Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show()
//                            finish()
//                        }
//                        .addOnFailureListener { exception ->
//                            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
//                            Log.e(TAG, "Error updating user", exception)
//                        }
//                } else {
//                    // Handle the case where no matching document was found
//                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
//                }
//            }
//            .addOnFailureListener { exception ->
//                Toast.makeText(this, "Query failed", Toast.LENGTH_SHORT).show()
//                Log.e(TAG, "Error querying user", exception)
//            }
//    }
//}

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