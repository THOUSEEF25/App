package com.example.app.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Spinner
import android.widget.TextView
import com.example.app.R
import com.example.app.model.User


//class UserAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val user = userList[position]
//        holder.nameTextView.text =user.name
//        holder.phoneTextView.text = user.phone
//        holder.followUpDateTextView.text = user.followUpDate
//
//    }
//
//    override fun getItemCount(): Int {
//        return userList.size
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
//        val phoneTextView: TextView = itemView.findViewById(R.id.phoneTextView)
//        val followUpDateTextView: TextView = itemView.findViewById(R.id.followUpDateTextView)
//    }
//}


class UserAdapter(private val context: Context, private val userList: List<User>) : BaseAdapter() {

    override fun getCount(): Int {
        return userList.size
    }

    override fun getItem(position: Int): Any {
        return userList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val user = userList[position]
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_user, parent, false)

        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val phoneTextView = itemView.findViewById<TextView>(R.id.phoneTextView)
        val followupDateTextView = itemView.findViewById<TextView>(R.id.followUpDateTextView)
        val statusSpinner = itemView.findViewById<Spinner>(R.id.statusSpinner)

        nameTextView.text = user.name
        phoneTextView.text = user.phone
        followupDateTextView.text = user.followUpDate

        val statusArray = arrayOf("Followup", "Drop", "Hold", "Win")
        val statusAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, statusArray)
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        statusSpinner.adapter = statusAdapter

        statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val selectedStatus = statusArray[pos]
                user.status = selectedStatus
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        return itemView
    }
}
