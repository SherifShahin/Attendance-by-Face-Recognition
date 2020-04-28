package com.graduationproject.Model

import com.google.gson.annotations.SerializedName
import com.graduationproject.DatabaseModel.DoctorStudents

data class DoctorGroupsResponse(
    val students : List<DoctorStudentElement> ,
    @SerializedName("_id") val groupId : String ,
    @SerializedName("name") val groupName : String
)
