package com.graduationproject.Service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.graduationproject.Dao.Dao
import com.graduationproject.Data.Api
import com.graduationproject.FileUtil
import com.graduationproject.Model.StudentAttendanceServiceModel
import com.graduationproject.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.koin.android.ext.android.get
import retrofit2.Call
import retrofit2.Response
import java.io.File


class StudentAttendanceService : Service()
{
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
    {
        Log.e("service","")

        val b: Bundle = intent?.extras!!
        val Student = b.getParcelable<Parcelable>("studentAttendance") as StudentAttendanceServiceModel

        val imageUri = Student.image

        val groupId = Student.groupId

        val attendanceId = Student.attendanceId

        val file = File(imageUri.path)

        val image = File(FileUtil.getPath(imageUri, application.applicationContext!!))

        val requestFile: RequestBody = RequestBody.create(
            "*/*".toMediaTypeOrNull(),
            image)


        val requestimage : MultipartBody.Part =
            MultipartBody.Part.createFormData("image", file.name, requestFile)


        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) getNotificationChannel(
                notificationManager
            ) else ""


       val notificationBuilder : NotificationCompat.Builder = NotificationCompat.Builder(this, channelId!!)
            .setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Sending image")
            .setCategory(NotificationCompat.CATEGORY_SERVICE)

        notificationBuilder.setProgress(0,0,true)

        notificationManager.notify(80,notificationBuilder.build())

        CoroutineScope(Dispatchers.IO).launch {

            val dao = application.get<Dao>()

            val header = "Bearer "+ dao.getUser().token

            Api().StudentAttendance(header , groupId , attendanceId , requestimage)
                .enqueue(object : retrofit2.Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        notificationBuilder.setContentText("upload failed  ${t.message}")
                        notificationBuilder.setProgress(0,0,false)
                        notificationBuilder.setOngoing(false)
                        notificationManager.notify(80,notificationBuilder.build())
                        stopForeground(false)
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {

                        if(response.isSuccessful)
                        {
                            notificationBuilder.setContentText("Upload completed")
                            notificationBuilder.setProgress(0,0,false)
                            notificationBuilder.setOngoing(false)
                            notificationManager.notify(80,notificationBuilder.build())
                            stopForeground(false)
                        }
                        else{
                            notificationBuilder.setContentText("upload failed")
                            notificationBuilder.setProgress(0,0,false)
                            notificationBuilder.setOngoing(false)
                            notificationManager.notify(80,notificationBuilder.build())
                            stopForeground(false)
                        }
                    }

                })
        }

        startForeground(80, notificationBuilder.build())

        return START_NOT_STICKY
    }


    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun getNotificationChannel(notificationManager: NotificationManager): String? {
        val channelId = "channelid"
        val channelName = resources.getString(R.string.app_name)
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        channel.importance = NotificationManager.IMPORTANCE_NONE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        notificationManager.createNotificationChannel(channel)
        return channelId
    }

}