package com.example.app.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.activity.EditUserActivity
import com.example.app.model.User

class UserAdapter(private var userList: MutableList<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val phoneTextView: TextView = itemView.findViewById(R.id.phoneTextView)
        val followupDateTextView: TextView = itemView.findViewById(R.id.followUpDateTextView)
        val statusSpinner: Spinner = itemView.findViewById(R.id.statusSpinner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]

        holder.nameTextView.text = user.name
        holder.phoneTextView.text = user.phone
        holder.followupDateTextView.text = user.followUpDate

        val statusArray = arrayOf("Followup", "Drop", "Hold", "Win")
        val statusAdapter = ArrayAdapter(holder.itemView.context, android.R.layout.simple_spinner_item, statusArray)
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.statusSpinner.adapter = statusAdapter

        holder.statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val selectedStatus = statusArray[pos]
                user.status = selectedStatus
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Set click listeners for edit and delete buttons
        holder.itemView.setOnClickListener {
            // Handle item click - Open update activity with user's data
            val intent = Intent(holder.itemView.context, EditUserActivity::class.java)
            intent.putExtra("editUser", user)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}