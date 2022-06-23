package com.example.bb.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.bb.database.Case
import com.example.bb.database.User

data class PatientAndCase(
    @Embedded var user: User = User(0L,"","",0L,"","",0L,"","",0,-1),
    @Relation(
        parentColumn = "id",
        entityColumn = "user_id",
        entity = Case::class
    )
    var case: Case = Case(0L,"","",0.0,0.0,0.0,"","","",0L,0L)
    )