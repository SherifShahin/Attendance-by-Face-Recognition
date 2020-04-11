package com.graduationproject.DatabaseModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DoctorGroupsStudentsRelation(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id  : Int = 0,

    @ColumnInfo(name = "group_id")
    val groupId  :String ,

    @ColumnInfo(name = "student_id")
    val studentId  :String
)