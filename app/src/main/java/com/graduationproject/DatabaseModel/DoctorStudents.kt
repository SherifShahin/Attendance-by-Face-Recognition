package com.graduationproject.DatabaseModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DoctorStudents(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "student_id")
    val _id : String ,

    @ColumnInfo(name = "student_name")
    val name : String ,

    @ColumnInfo(name = "student_department")
    val department : String
)