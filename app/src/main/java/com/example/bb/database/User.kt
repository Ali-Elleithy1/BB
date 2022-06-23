package com.example.bb.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("users_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "fname")
    var firstName: String = "",
    @ColumnInfo(name = "lname")
    var lastName: String = "",
    @ColumnInfo(name = "mobile")
    var mobile: Long = 0L,
    @ColumnInfo(name = "email")
    var email: String = "",
    @ColumnInfo(name = "password")
    var password: String = "",
    @ColumnInfo(name = "natId")
    var natId: Long = 0L,
    @ColumnInfo(name = "governorate")
    var governorate: String = "",
    @ColumnInfo(name = "case")
    var category: String = "",
    @ColumnInfo(name = "age")
    var age: Int = 0,
    @ColumnInfo(name = "purpose")
    var purpose: Int = -1
)