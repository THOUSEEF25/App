package com.example.app.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.app.R
import com.example.app.databinding.ActivityHomeBinding
import com.example.app.registration.LoginActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logout.setOnClickListener {
            logout()
        }

        val addNewUserCard = findViewById<CardView>(R.id.cardAddNewUser)
        addNewUserCard.setOnClickListener {
            startActivity(Intent(this, AddNewUserActivity::class.java))
        }

        val followUpCard = findViewById<CardView>(R.id.cardFollowup)
        followUpCard.setOnClickListener {
            startActivity(Intent(this, FollowupActivity::class.java))
        }

        val profileCard = findViewById<CardView>(R.id.cardProfile)
        profileCard.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun logout() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Alert!")
        alert.setMessage("Your trying to logout be careful!!")
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Logout")
        alertDialogBuilder.setMessage("Are you sure you want to logout?")

        alertDialogBuilder.setPositiveButton("Confirm") { _, _ ->
            performLogout()
            Toast.makeText(this,"logout successful", Toast.LENGTH_SHORT).show()
        }
        alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun performLogout() {
        val sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}