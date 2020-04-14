package com.graduationproject.Repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.graduationproject.Dao.Dao
import com.graduationproject.Data.Api
import com.graduationproject.Model.DoctorAddGroupModel
import com.graduationproject.Model.ErrorResponse

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.lang.Error


class DoctorAddGroupRepository(val dao : Dao)
{
    private var _Added : MutableLiveData<Boolean> = MutableLiveData()

    val Added : LiveData<Boolean> = _Added

    private var _ErrorMessage : MutableLiveData<String> = MutableLiveData()

    val ErrorMessage : LiveData<String> = _ErrorMessage

    fun AddGroup(groupName : String)
    {
        CoroutineScope(Dispatchers.IO).launch {

            val header = "Bearer "+ dao.getUser().token

            val api = Api().DoctorAddGroup(header , DoctorAddGroupModel(groupName))

            api.enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("failure",t.message)
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>) {

                 if(response.isSuccessful) {
                     _Added.postValue(true)
                 }

                 else {
                     try {
                         val gson = Gson()

                         val type = object : TypeToken<ErrorResponse>() {
                         }.type

                         val errorResponse = gson.fromJson<ErrorResponse>(response.errorBody()!!.charStream(), type)

                         _ErrorMessage.postValue(errorResponse.error)

                     } catch (e: Exception) {
                         _ErrorMessage.postValue("something wrong try again later")
                     }
                 }
                }
            })

        }
    }
}