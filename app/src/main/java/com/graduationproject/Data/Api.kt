package com.graduationproject.Data

import com.graduationproject.Model.AdminRegisterModel
import com.graduationproject.Model.LoginModel
import com.graduationproject.Model.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface Api
{
    @POST("user/login")
    fun userLogin(
        @Body user :  LoginModel
    ) : Call<LoginResponse>

    @POST("user/register/{role}")
    fun AdminRegister(
        @Header("Authorization") header : String,
        @Body user :  AdminRegisterModel ,
        @Path("role") role : String
    ) : Call<ResponseBody>

    companion object  {
        operator fun invoke() : Api{
            return  Retrofit.Builder()
                .baseUrl("http://8b9209a7.ngrok.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }
}