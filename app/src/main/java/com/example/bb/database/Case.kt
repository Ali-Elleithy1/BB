package com.example.bb.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cases_table")
data class Case(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var category: String ="",
    @ColumnInfo(name = "priority_level")
    var priorityLevel: String = "",
    @ColumnInfo(name = "donations_needed")
    var donationsNeeded: Double = 0.0,
    @ColumnInfo(name = "donations_paid")
    var donationsPaid: Double = 0.0,
    @ColumnInfo(name = "total_donations")
    var totalDonations: Double = 0.0,
    var description: String = "",
    var criticality: String = "",
    var status: String = "",
    @ColumnInfo(name = "user_id")
    var userId: Long = 0L,
    @ColumnInfo(name = "charity_id")
    var charityId: Long = 0L
)