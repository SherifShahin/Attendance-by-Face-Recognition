package com.graduationproject.Data

import com.graduationproject.Model.AdminRegisterModel
import com.graduationproject.Model.DoctorGroupsResponse
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


    @GET("group")
    fun getDoctorGroups(
        @Header("Authorization") header : String
    ) : Call<List<DoctorGroupsResponse>>

    companion object  {
        operator fun invoke() : Api{
            return  Retrofit.Builder()
                .baseUrl("https://face-recognition-gp.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }
}