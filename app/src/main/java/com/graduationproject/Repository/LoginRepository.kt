package com.graduationproject.Repository

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatApp.Dao.Dao
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.graduationproject.Data.Api
import com.graduationproject.DatabaseModel.User
import com.graduationproject.Model.ErrorResponse
import com.graduationproject.Model.LoginModel
import com.graduationproject.Model.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class LoginRepository(private val dao : Dao)
{
    private val _login : MutableLiveData<String> = MutableLiveData()

    val login : LiveData<String> = _login

    fun userLogin(user : LoginModel)
    {
        val email = user.email
        val password = user.password

        if(email.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            _login.value = "invalid email"
             return
        }

        if(password.isNullOrEmpty() || password.length < 8)
        {
            _login.value = "invalid password"
            return
        }

        val api = Api().userLogin(user)

        CoroutineScope(Dispatchers.IO).launch {

            api.enqueue(object : retrofit2.Callback<LoginResponse> {

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    _login.postValue(t.message)
                }

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                    if (response.isSuccessful)
                    {
                        val token = response.body()?.token
                        saveToken(token , user.role)
                        Log.e("token",token)
                        _login.postValue("successful")
                    }
                    else
                    {
                        try {
                            val gson = Gson()

                            val type = object : TypeToken<ErrorResponse>() {
                            }.type

                            val errorResponse = gson.fromJson<ErrorResponse>(response.errorBody()!!.charStream(), type)

                            _login.value = errorResponse.error

                        } catch (e: Exception) {
                            _login.postValue("something wrong try again later")
                        }
                    }
                }
            })
        }
    }

    private fun saveToken(token: String?, role: String)
    {
        CoroutineScope(Dispatchers.IO).launch {
         dao.setUser(User(token!!,role))
        }
    }
}