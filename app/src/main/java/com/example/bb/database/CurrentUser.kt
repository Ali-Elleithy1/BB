package com.example.bb.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_user")
data class CurrentUser(
    @PrimaryKey(autoGenerate = false)
    var currentUserId: Long = 0L
)