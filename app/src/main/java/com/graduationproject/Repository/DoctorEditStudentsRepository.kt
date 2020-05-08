package com.graduationproject.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.graduationproject.Dao.Dao
import com.graduationproject.Data.Api

import com.graduationproject.DatabaseModel.DoctorStudents
import com.graduationproject.Model.ErrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response


class DoctorEditStudentsRepository(val dao: Dao, groupId : String)
{
    val students : LiveData<List<DoctorStudents>> = dao.getGroupStudents(groupId)

    private var _requestResult : MutableLiveData<String> = MutableLiveData()
    val requestResult : LiveData<String> = _requestResult

    fun done(groupId: String) {

        CoroutineScope(Dispatchers.IO).launch {

            val header = "Bearer "+ dao.getUser().token

            val api = Api().DoctorEditStudentsDone(header , groupId)

            api.enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    _requestResult.postValue(t.message)
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful)
                    {
                        _requestResult.postValue("done")
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
}