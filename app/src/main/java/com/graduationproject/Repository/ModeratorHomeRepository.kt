package com.graduationproject.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.graduationproject.Dao.Dao
import com.graduationproject.Data.Api
import com.graduationproject.Model.ModeratorGetStudentsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class ModeratorHomeRepository(val dao : Dao)
{

    private val _allStudents : MutableLiveData<List<ModeratorGetStudentsResponse>> = MutableLiveData()
    val allStudents : LiveData<List<ModeratorGetStudentsResponse>> = _allStudents

    fun getAllStudents(searchText : String = "")
    {
        CoroutineScope(Dispatchers.IO).launch {

            val header = "Bearer "+ dao.getUser().token

            val api = Api().ModeratorgetStudents(header,searchText)

            api.enqueue(object : retrofit2.Callback<List<ModeratorGetStudentsResponse>>{
                override fun onFailure(
                    call: Call<List<ModeratorGetStudentsResponse>>,
                    t: Throwable){
                    Log.e("failure", t.message)
                }

                override fun onResponse(
                    call: Call<List<ModeratorGetStudentsResponse>>,
                    response: Response<List<ModeratorGetStudentsResponse>>
                ) {
                    if(response.isSuccessful) {
                        val list = response.body()
                        _allStudents.postValue(list)
                    }
                    else
                        Log.e("error",""+response.code())
                }
            })
        }
    }

}