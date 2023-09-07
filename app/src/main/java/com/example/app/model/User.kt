package com.example.app.model

import java.io.Serializable

data class User(
    var name: String = "",
    var email: String = "",
    var phone: String = "",
    var address: String = "",
    var followUpDate: String = "",
    var planTo: String = "",
    var description: String = ""
) : Serializable {
    val documentId: String = ""
    var status: String = ""
}
