package com.example.bb.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "charities_table")
data class Charity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "image")
    var image: String = ""
)