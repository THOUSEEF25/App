package com.example.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.adapter.UserAdapter
import com.example.app.databinding.ActivityFollowupBinding
import com.example.app.databinding.ActivityLoginBinding
import com.example.app.model.User
import com.google.firebase.firestore.FirebaseFirestore

class FollowupActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var userRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followup)

        db = FirebaseFirestore.getInstance()
        userRecyclerView = findViewById(R.id.userRecyclerView)

        setupRecyclerView()
        fetchAndDisplayUserList()
    }

    private fun setupRecyclerView() {
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        // You can also customize the layout manager and decoration here
    }

    private fun fetchAndDisplayUserList() {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val userList = ArrayList<User>()
                for (document in result) {
                    val user = document.toObject(User::class.java)
                    userList.add(user)
                }

                val userAdapter = UserAdapter(userList)
                userRecyclerView.adapter = userAdapter
            }
            .addOnFailureListener { exception ->
                // Handle error
                Log.e(TAG, "Error fetching user data", exception)
            }
    }

    companion object {
        private const val TAG = "FollowupActivity"
    }
}