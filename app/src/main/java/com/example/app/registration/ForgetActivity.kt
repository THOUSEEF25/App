package com.example.app.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.app.R
import com.example.app.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth

class ForgetActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
//    private lateinit var newPasswordEditText: EditText
    private lateinit var sendResetEmailButton: Button
//    private lateinit var changePasswordButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget)

        auth = FirebaseAuth.getInstance()

        emailEditText = findViewById(R.id.editTextEmail)
//        newPasswordEditText = findViewById(R.id.editTextNewPassword)
        sendResetEmailButton = findViewById(R.id.buttonSendResetEmail)
//        changePasswordButton = findViewById(R.id.buttonChangePassword)

        sendResetEmailButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show()
            } else {
                sendPasswordResetEmail(email)
            }
        }

//        changePasswordButton.setOnClickListener {
//            val newPassword = newPasswordEditText.text.toString()
//
//            if (newPassword.isEmpty()) {
//                Toast.makeText(this, "Please enter a new password.", Toast.LENGTH_SHORT).show()
//            } else {
//                changePassword(newPassword)
//            }
//        }
    }

    private fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    // changePasswordButton.isEnabled = true
                } else {
                    Toast.makeText(this, "Failed to send password reset email.", Toast.LENGTH_SHORT).show()
                }
            }
    }

//    private fun changePassword(newPassword: String) {
//        val user = auth.currentUser
//        user?.updatePassword(newPassword)
//            ?.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(this, "Password changed successfully.", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, "Failed to change password.", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
}