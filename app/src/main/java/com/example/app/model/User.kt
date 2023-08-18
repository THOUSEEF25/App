package com.example.app.model

import java.io.Serializable

data class User(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val followUpDate: String = ""
) : Serializable
