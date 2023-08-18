package com.example.app.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.app.R
import com.example.app.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.textViewLogin.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.buttonRegister.setOnClickListener {
            val email = binding.editTextEmailAddress.text.toString()
            val password = binding.editTextPassword.text.toString()
            val rePassword = binding.editTextRePassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && rePassword.isNotEmpty()) {
                if (password == rePassword){
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Registered Successfully" , Toast.LENGTH_LONG).show()
                            val intent = Intent(this,LoginActivity::class.java)
                            startActivity(intent)
                            auth.currentUser?.sendEmailVerification()
                            auth.signOut()
                            finish()
                        }else {
                            Toast.makeText(this, it.exception.toString() , Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Password isn't Matching" , Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please Enter Details" , Toast.LENGTH_LONG).show()
            }
        }
    }
}