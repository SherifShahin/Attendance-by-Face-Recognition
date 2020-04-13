package com.graduationproject.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.graduationproject.Dao.Dao
import com.graduationproject.Data.Api
import com.graduationproject.Model.AddStudentModel
import com.graduationproject.Model.DoctorStudentsSearchResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class DoctorGroupAddStudentRepository(val dao : Dao)
{
    private val _searchresult : MutableLiveData<List<DoctorStudentsSearchResponse>> = MutableLiveData()
    val searchresult : LiveData<List<DoctorStudentsSearchResponse>> = _searchresult

    private val _StudentAdded : MutableLiveData<Boolean> = MutableLiveData()
    val StudentAdded : LiveData<Boolean> = _StudentAdded

    fun serach(searchText : String)
    {
        CoroutineScope(Dispatchers.IO).launch {

            val header = "Bearer "+ dao.getUser().token

            val api = Api().getStudents(header,searchText)

            api.enqueue(object : retrofit2.Callback<List<DoctorStudentsSearchResponse>>{
                override fun onFailure(
                    call: Call<List<DoctorStudentsSearchResponse>>,
                    t: Throwable){
                    Log.e("failure", t.message)
                }

                override fun onResponse(
                    call: Call<List<DoctorStudentsSearchResponse>>,
                    response: Response<List<DoctorStudentsSearchResponse>>) {
                    if(response.isSuccessful) {
                        val list = response.body()
                        _searchresult.postValue(list)
                    }
                    else
                        Log.e("error",""+response.code())
                }
            })
        }
    }

    fun addStudent(student: DoctorStudentsSearchResponse, groupId: String)
    {
        CoroutineScope(Dispatchers.IO).launch {

            val header = "Bearer "+ dao.getUser().token

            val api = Api().addStudentToGroup(header , AddStudentModel(student.studentId) , groupId)

            api.enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("failure",t.message)
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ){
                    if(response.isSuccessful)
                    {
                        _StudentAdded.postValue(true)
                        Log.e("success",""+response.code())
                    }
                    else
                        Log.e("error",""+response.code())
                }
            })
        }
    }
}