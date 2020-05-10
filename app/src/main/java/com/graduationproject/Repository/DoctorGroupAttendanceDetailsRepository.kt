package com.graduationproject.Repository

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.graduationproject.Dao.Dao
import com.graduationproject.Data.Api
import com.graduationproject.Model.DoctorStudentsSearchResponse
import com.graduationproject.Model.ErrorResponse
import com.graduationproject.Model.StudentAttendanceServiceModel
import com.graduationproject.Service.StudentAttendanceService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class DoctorGroupAttendanceDetailsRepository(val dao : Dao ,val  application: Application)
{
    private var _requestResult : MutableLiveData<String> = MutableLiveData()
    val requestResult : LiveData<String> = _requestResult


    private var _AttendanceList : MutableLiveData<List<DoctorStudentsSearchResponse>> = MutableLiveData()
    val AttendanceList : LiveData<List<DoctorStudentsSearchResponse>> = _AttendanceList


    fun getAttendees(groupId : String , attendanceId : String)
    {

        CoroutineScope(Dispatchers.IO).launch {
            val header = "Bearer " + dao.getUser().token

            val api = Api().getGroupAttendees(header, groupId, attendanceId)

            api.enqueue(object : retrofit2.Callback<List<DoctorStudentsSearchResponse>>{
                override fun onFailure(
                    call: Call<List<DoctorStudentsSearchResponse>>,
                    t: Throwable
                ) {
                    _requestResult.postValue(t.message)
                }

                override fun onResponse(
                    call: Call<List<DoctorStudentsSearchResponse>>,
                    response: Response<List<DoctorStudentsSearchResponse>>
                ) {
                    if(response.isSuccessful)
                    {
                        _AttendanceList.postValue(response.body())
                        Log.e("attendees",response.body().toString())
                    }
                    else
                    {
                        try {
                            val gson = Gson()

                            val type = object : TypeToken<ErrorResponse>() {
                            }.type

                            val errorResponse = gson.fromJson<ErrorResponse>(response.errorBody()!!.charStream(), type)

                            _requestResult.value = errorResponse.error

                        } catch (e: Exception) {
                            _requestResult.postValue("something wrong try again later")
                        }
                    }
                }

            })
        }
    }

    fun getAbsent(groupId: String , attendanceId: String)
    {
        CoroutineScope(Dispatchers.IO).launch {

            val header = "Bearer " + dao.getUser().token

            val api = Api().getGroupAbsent(header, groupId, attendanceId)

            api.enqueue(object : retrofit2.Callback<List<DoctorStudentsSearchResponse>>{
                override fun onFailure(call: Call<List<DoctorStudentsSearchResponse>>,
                    t: Throwable)
                {
                    _requestResult.postValue(t.message)
                }

                override fun onResponse(call: Call<List<DoctorStudentsSearchResponse>>,
                    response: Response<List<DoctorStudentsSearchResponse>>)
                {
                    if(response.isSuccessful)
                    {
                        _AttendanceList.postValue(response.body())
                        Log.e("absent",response.body().toString())
                    }
                    else
                    {
                        try {
                            val gson = Gson()

                            val type = object : TypeToken<ErrorResponse>() {
                            }.type

                            val errorResponse = gson.fromJson<ErrorResponse>(response.errorBody()!!.charStream(), type)

                            _requestResult.value = errorResponse.error

                        } catch (e: Exception) {
                            _requestResult.postValue("something wrong try again later")
                        }
                    }
                }
            })
        }
    }

    fun MakeStudentAttendance(imageuri : Uri , groupId: String , attendanceId: String)
    {
        Log.e("repository",imageuri.toString())
        val StudentAttendance = StudentAttendanceServiceModel(imageuri , groupId, attendanceId)

        val intent = Intent(application.applicationContext , StudentAttendanceService::class.java)

        val b = Bundle()
        b.putParcelable("studentAttendance", StudentAttendance)
        intent.putExtras(b)

        application.startService(intent)
    }
}