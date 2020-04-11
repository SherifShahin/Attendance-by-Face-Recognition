package com.graduationproject.Repository

import androidx.lifecycle.LiveData
import com.graduationproject.Dao.Dao

import com.graduationproject.DatabaseModel.DoctorStudents


class DoctorGroupStudentsRepository(val dao: Dao, groupId : String)
{
    val students : LiveData<List<DoctorStudents>> = dao.getGroupStudents(groupId)
}