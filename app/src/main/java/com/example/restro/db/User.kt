package com.example.restro.db

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val username: String? = "",
    val email: String? = "",
    val number: String? = "",
    val password: String? = ""
)