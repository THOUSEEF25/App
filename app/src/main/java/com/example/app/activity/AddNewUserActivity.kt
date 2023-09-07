package com.example.app.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.databinding.ActivityAddNewUserBinding
import com.example.app.model.User
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

        binding.planTo.setOnClickListener {
            planTo()
        }

        binding.followUpDateEditText.setOnClickListener {
            showDateTimePickerDialog()
        }
    }

    private fun saveUserData() {
        val name = binding.name.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val phone = binding.phone.text.toString().trim()
        val address = binding.address.text.toString().trim()
        val date = binding.followUpDateEditText.text.toString().trim()
        val plan = binding.planTo.text.toString().trim()
        val description = binding.description.text.toString().trim()

        if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && address.isNotEmpty() && date.isNotEmpty() && plan.isNotEmpty() && description.isNotEmpty()) {
            val newUser = User(name, email, phone, address, date , plan , description)
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

    private fun showDateTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                showTimePickerDialog(selectedYear, selectedMonth, selectedDay)
            },
            year, month, day
        )

        datePicker.show()
    }

    private fun showTimePickerDialog(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePicker = TimePickerDialog(
            this,
            { _, _, _ ->
                val dateTime = "${month + 1}/$day/$year"
                binding.followUpDateEditText.setText(dateTime)
            },
            hour, minute, false
        )

        timePicker.show()
    }

    private fun planTo() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        val datePicker = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                showTimePicker(selectedYear, selectedMonth, selectedDay)
            },
            year, month, day
        )

        datePicker.show()
    }

    private fun showTimePicker(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePicker = TimePickerDialog(
            this,
            { _, _, _ ->
                val formattedDate = "${month + 1}/$day/$year"
                binding.planTo.setText(formattedDate)
            },
            hour, minute, false
        )

        timePicker.show()
    }

}