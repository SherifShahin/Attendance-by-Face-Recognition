package com.graduationproject.DatabaseModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DoctorGroups(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "group_id")
    val groupId  :String ,

    @ColumnInfo(name = "group_name")
    val groupName : String
)