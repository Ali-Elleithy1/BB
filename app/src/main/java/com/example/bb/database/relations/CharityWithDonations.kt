package com.example.bb.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.bb.database.Charity
import com.example.bb.database.Donation

//data class CharityWithDonations (
//    @Embedded val charity: Charity,
//    @Relation(
//        parentColumn = "name",
//        entityColumn = "charity_name"
//    )
//    val donations: List<Donation>
//)