package com.graduationproject.Repository

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.graduationproject.Model.SendVideoServiceModel
import com.graduationproject.Service.SendVideoService


class ModeratorAddStudentRepository (val application: Application)
{
    private var _Submit : MutableLiveData<String> = MutableLiveData()
    val Submit : LiveData<String> = _Submit

    fun AddNewStudent(name : String , email : String , department : String , videouri : Uri)
    {
        if(name.isNullOrEmpty())
        {
            _Submit.value = "invalid name"
            return
        }


        if(email.isNullOrEmpty())
        {
            _Submit.value = "invalid email"
            return
        }

        if(department.isNullOrEmpty()) {
            _Submit.value = "invalid department"
            return
        }

        if(videouri == null)
        {
            _Submit.value = "invalid video"
            return
        }


        val newStudent = SendVideoServiceModel(name , email ,department , videouri)

        val intent = Intent(application.applicationContext , SendVideoService::class.java)
        val b = Bundle()
        b.putParcelable("student", newStudent)
        intent.putExtras(b)

        application.startService(intent)
    }



    fun setSubmitStatus(status : String)
    {
        _Submit.postValue(status)
    }

}