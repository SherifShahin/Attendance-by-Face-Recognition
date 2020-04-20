package com.graduationproject.Model

import com.google.gson.annotations.SerializedName

data class ModeratorGetStudentsResponse(
    @SerializedName("_id")
    val studentId : String ,
    @SerializedName("name")
    val studentName : String ,
    @SerializedName("department") val department : String )