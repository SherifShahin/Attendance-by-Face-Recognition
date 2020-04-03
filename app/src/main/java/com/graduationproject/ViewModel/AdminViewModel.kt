package com.graduationproject.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.graduationproject.Model.AdminRegisterModel
import com.graduationproject.Model.LoginModel
import com.graduationproject.Repository.AdminRepository
import org.koin.android.ext.android.get

class AdminViewModel (application: Application) : AndroidViewModel(application)
{
    private val repository = application.get<AdminRepository>()

    private val register : LiveData<String>

    init {
        register = repository.register
    }

    fun userRegister(user : AdminRegisterModel)
    {
        repository.userRegister(user)
    }

    fun getRegister():LiveData<String>
    {
        return register
    }


}