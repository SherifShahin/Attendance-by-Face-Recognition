package com.graduationproject.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.graduationproject.Dao.Dao

import com.graduationproject.Data.Api
import com.graduationproject.DatabaseModel.DoctorGroups
import com.graduationproject.DatabaseModel.DoctorGroupsStudentsRelation
import com.graduationproject.DatabaseModel.DoctorStudents

import com.graduationproject.Model.DoctorGroupsResponse

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Response

class DoctorHomeRepository(val dao: Dao)
{
    val groups : LiveData<List<DoctorGroups>> = dao.getDoctorGroup()

    fun getGroups()
    {
        CoroutineScope(Dispatchers.IO).launch {

            val header = "Bearer "+ dao.getUser().token

            val api = Api().getDoctorGroups(header)

            api.enqueue(object : retrofit2.Callback<List<DoctorGroupsResponse>>{
                override fun onFailure(call: Call<List<DoctorGroupsResponse>>, t: Throwable) {
                    Log.e("failure",t.message)
                }

                override fun onResponse(call: Call<List<DoctorGroupsResponse>> , response: Response<List<DoctorGroupsResponse>>) {

                    if(response.isSuccessful)
                    {
                        insertGroups(response.body())
                    }
                    else
                        Log.e("error",""+response.code())
                }
            })
        }
    }

    private fun insertGroups(groups : List<DoctorGroupsResponse>?)
    {
        CoroutineScope(Dispatchers.IO).launch {

            dao.deleteRelations()

            groups?.forEach {

                val students: List<DoctorStudents> = it.students

                val groupid = it.groupId

                dao.setDoctorGroup(DoctorGroups(it.groupId, it.groupName))

                students.let {

                    it.forEach {
                        dao.setDoctorStudent(
                            it,
                            DoctorGroupsStudentsRelation(groupId = groupid, studentId = it._id)
                        )
                    }
                }
            }
        }
    }

}