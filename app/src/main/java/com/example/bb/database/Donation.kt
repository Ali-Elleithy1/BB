package com.example.bb.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "donations_table")
data class Donation(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo("amount_donated")
    var amountDonated: Double = 0.0,
    @ColumnInfo(name = "user_id")
    var userId: Long = 0L,
    @ColumnInfo(name = "case_num")
    var caseNum: Long = 0L,
    @ColumnInfo(name = "charity_name")
    var charityName: String = ""
)