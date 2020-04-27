package com.graduationproject.ViewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.graduationproject.Repository.ModeratorAddStudentRepository
import org.koin.android.ext.android.get

class ModeratorAddStudentViewModel (application: Application) : AndroidViewModel(application)
{
    private val repository = application.get<ModeratorAddStudentRepository>()

    val Submit : LiveData<String>

    init {
        Submit = repository.Submit
    }

    fun getSubmitStatus() : LiveData<String>
    {
        return Submit
    }


    fun addNewStudent(name : String , email : String , department : String , videouri : Uri)
    {
        repository.AddNewStudent(name , email , department , videouri)
    }

}