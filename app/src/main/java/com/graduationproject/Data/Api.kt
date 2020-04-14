package com.graduationproject.Data

import com.graduationproject.Model.*
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


    @GET("/api/student/search")
    fun getStudents(
        @Header("Authorization") header : String,
        @Query("name") name : String
    )  : Call<List<DoctorStudentsSearchResponse>>


    @POST("group/{groupId}/add")
    fun addStudentToGroup(
        @Header("Authorization") header : String,
        @Body studentId : AddStudentModel ,
        @Path("groupId") groupId : String
    ) : Call<ResponseBody>


    @POST("group/new")
    fun DoctorAddGroup(
        @Header("Authorization") header : String,
        @Body groupName : DoctorAddGroupModel
    ) : Call<ResponseBody>


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