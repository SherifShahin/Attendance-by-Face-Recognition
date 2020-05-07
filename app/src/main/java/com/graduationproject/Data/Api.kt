package com.graduationproject.Data

import com.graduationproject.Model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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


    @GET("/api/student/search")
    fun ModeratorgetStudents(
        @Header("Authorization") header : String,
        @Query("name") name : String
    )  : Call<List<ModeratorGetStudentsResponse>>

    @Multipart
    @POST("student/new")
    fun uploadVideo(
        @Header("Authorization") header : String ,
        @Part("name") name: RequestBody,
        @Part("email") email : RequestBody ,
        @Part("department") department: RequestBody,
        @Part video: MultipartBody.Part
    ) : Call<ResponseBody>


    @GET("group/{groupId}/attendance")
    fun getDoctorGroupAttendance(
        @Header("Authorization") header : String ,
        @Path("groupId") groupId: String
    ): Call<List<DoctorGroupAttendanceResponse>>


    @POST("group/{groupId}/new-attendance-record")
    fun setNewAttendance(
        @Header("Authorization") header : String ,
        @Path("groupId") groupId: String ,
        @Body date : DoctorGroupNewAttendance
    ) : Call<List<DoctorGroupAttendanceResponse>>

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