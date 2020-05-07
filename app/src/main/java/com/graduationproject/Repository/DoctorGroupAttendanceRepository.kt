package com.graduationproject.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.graduationproject.Dao.Dao
import com.graduationproject.Data.Api
import com.graduationproject.Model.DoctorGroupAttendanceResponse
import com.graduationproject.Model.DoctorGroupNewAttendance
import com.graduationproject.Model.ErrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DoctorGroupAttendanceRepository (val dao : Dao)
{
    private var _groupAttendace : MutableLiveData<List<DoctorGroupAttendanceResponse>> = MutableLiveData()
    val groupAttendance : LiveData<List<DoctorGroupAttendanceResponse>> = _groupAttendace


    private var _requestResult : MutableLiveData<String> = MutableLiveData()
    val requestResult : LiveData<String> = _requestResult


    fun getGroupAttendace(groupId : String)
    {
        CoroutineScope(Dispatchers.IO).launch {

            val header = "Bearer "+ dao.getUser().token

            val api = Api().getDoctorGroupAttendance(header , groupId)

            api.enqueue(object : retrofit2.Callback<List<DoctorGroupAttendanceResponse>>{
                override fun onFailure(
                    call: Call<List<DoctorGroupAttendanceResponse>>,
                    t: Throwable
                ) {
                    _requestResult.postValue(t.message)
                }

                override fun onResponse(
                    call: Call<List<DoctorGroupAttendanceResponse>>,
                    response: Response<List<DoctorGroupAttendanceResponse>>
                ) {
                    if(response.isSuccessful)
                    {
                        _groupAttendace.postValue(response.body())
                    }
                    else{
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

    fun setNewAttendance(groupId: String)
    {
        CoroutineScope(Dispatchers.IO).launch {
            val header = "Bearer "+ dao.getUser().token

            val df = SimpleDateFormat("dd/M/yyyy")

            val today = Date()

            val todayDate = df.format(today)

            val api = Api().setNewAttendance(header , groupId
                ,DoctorGroupNewAttendance(todayDate))

            api.enqueue(object : retrofit2.Callback<List<DoctorGroupAttendanceResponse>>{
                override fun onFailure(
                    call: Call<List<DoctorGroupAttendanceResponse>>,
                    t: Throwable
                ) {
                    _requestResult.postValue(t.message)
                }

                override fun onResponse(
                    call: Call<List<DoctorGroupAttendanceResponse>>,
                    response: Response<List<DoctorGroupAttendanceResponse>>
                ) {
                    if(response.isSuccessful){
                        _groupAttendace.postValue(response.body())
                    }
                    else{
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

}