package com.example.app.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.databinding.ActivityAddNewUserBinding
import com.example.app.model.User
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class AddNewUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewUserBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        binding.save.setOnClickListener {
            saveUserData()
        }

        binding.followUpDateEditText.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun saveUserData() {
        val name = binding.editText1.text.toString().trim()
        val email = binding.editText2.text.toString().trim()
        val phone = binding.editText3.text.toString().trim()
        val address = binding.editText4.text.toString().trim()
        val date = binding.followUpDateEditText.text.toString().trim()

        if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && address.isNotEmpty() && date.isNotEmpty()) {
            val newUser = User(name, email, phone, address, date)
            addNewUserToFirestore(newUser)

            val intent = Intent(this, FollowupActivity::class.java)
            intent.putExtra("user",newUser)
            startActivity(intent)
            finish()

        } else {
            Toast.makeText(this,"Enter Details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addNewUserToFirestore(user: User) {
        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                // Data added successfully
                Toast.makeText(this,"Data added successfully", Toast.LENGTH_SHORT).show()
                finish() // Close the activity after saving
            }
            .addOnFailureListener {
                // Handle error
                Toast.makeText(this,"Data added error", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "${selectedMonth + 1}/$selectedDay/$selectedYear"
                binding.followUpDateEditText.setText(formattedDate)
            },
            year, month, day
        )

        datePicker.show()
    }
}