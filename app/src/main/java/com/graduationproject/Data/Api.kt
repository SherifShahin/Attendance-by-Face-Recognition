package com.graduationproject.Data

import com.graduationproject.Model.LoginModel
import com.graduationproject.Model.LoginResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api
{
    @POST("user/login")
    fun userLogin( @Body user :  LoginModel) : Call<LoginResponse>

    companion object{
        operator fun invoke() : Api{
            return  Retrofit.Builder()
                .baseUrl("http://7e6f2cd2.ngrok.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }
}