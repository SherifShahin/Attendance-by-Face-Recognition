package com.graduationproject.DatabaseModel

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @PrimaryKey
    @ColumnInfo(name = "token")
    val token : String,

    @ColumnInfo(name = "role")
    val role : String

)