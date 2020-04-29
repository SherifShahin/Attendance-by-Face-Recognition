package com.graduationproject.Repository

import androidx.lifecycle.LiveData
import com.graduationproject.Dao.Dao

import com.graduationproject.DatabaseModel.DoctorStudents


class DoctorEditStudentsRepository(val dao: Dao, groupId : String)
{
    val students : LiveData<List<DoctorStudents>> = dao.getGroupStudents(groupId)
}