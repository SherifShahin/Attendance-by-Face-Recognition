package com.graduationproject.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.graduationproject.Model.LoginModel
import com.graduationproject.Repository.LoginRepository
import org.koin.android.ext.android.get

class LoginViewModel (application: Application) : AndroidViewModel(application)
{

    private val repository = application.get<LoginRepository>()

    private val login : LiveData<String>

    init {
        login = repository.login
    }

    fun userLogin(user : LoginModel)
    {
        repository.userLogin(user)
    }

    fun getLogin():LiveData<String>
    {
        return login
    }


}