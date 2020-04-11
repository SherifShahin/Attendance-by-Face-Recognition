package com.graduationproject.Repository

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.graduationproject.Dao.Dao
import com.graduationproject.Data.Api
import com.graduationproject.DatabaseModel.User
import com.graduationproject.Model.AdminRegisterModel
import com.graduationproject.Model.ErrorResponse
import com.graduationproject.Model.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class AdminRepository(val dao: Dao)
{
    private val _register : MutableLiveData<String> = MutableLiveData()

    val register : LiveData<String> = _register

    fun userRegister(user : AdminRegisterModel)
    {
        val name = user.name
        val email = user.email
        val password = user.password

        if(name.isNullOrEmpty())
        {
            _register.value = "invalid name"
            return
        }

        if(email.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            _register.value = "invalid email"
             return
        }

        if(password.isNullOrEmpty() || password.length < 8)
        {
            _register.value = "invalid password"
            return
        }


        CoroutineScope(Dispatchers.IO).launch {

            val header = "Bearer "+ dao.getUser().token


            val api = Api().AdminRegister(header , user , user.role)

            api.enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    _register.postValue(t.message)
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>)
                {
                    if(response.isSuccessful)
                    {
                        _register.postValue("successful")
                    }
                    else
                    {
                        try {
                            val gson = Gson()

                            val type = object : TypeToken<ErrorResponse>() {
                            }.type

                            val errorResponse = gson.fromJson<ErrorResponse>(response.errorBody()!!.charStream(), type)

                            _register.value = errorResponse.error

                        } catch (e: Exception) {
                            _register.postValue("something wrong try again later")
                        }
                    }
                }
            })
        }
    }
}