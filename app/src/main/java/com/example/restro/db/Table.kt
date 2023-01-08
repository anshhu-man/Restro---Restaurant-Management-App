package com.example.restro.db

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Table(
    val name: String? = "",
    val t1: Boolean? = false,
    val t2: Boolean? = false,
    val t3: Boolean? = false,
    val t4: Boolean? = false,
    val t5: Boolean? = false,
    val t6: Boolean? = false,
    val t7: Boolean? = false,
    val t8: Boolean? = false,
    val t9: Boolean? = false,
    val t10: Boolean? = false
)