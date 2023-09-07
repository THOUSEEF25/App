package com.example.app.activity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.adapter.UserAdapter
import com.example.app.databinding.ActivityFollowupBinding
import com.example.app.model.User
import com.google.firebase.firestore.FirebaseFirestore


class FollowupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFollowupBinding
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var db: FirebaseFirestore
    private lateinit var userList: List<User>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFollowupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        userRecyclerView = findViewById(R.id.userRecyclerView)

        val layoutManager = LinearLayoutManager(this)
        userRecyclerView.layoutManager = layoutManager

        fetchAndDisplayUserList()
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

                val userAdapter = UserAdapter(userList, db)
                userRecyclerView.adapter = userAdapter
            }
            .addOnFailureListener { exception ->
                // Handle error
                Log.e(TAG, "Error fetching user data", exception)
            }
    }
}