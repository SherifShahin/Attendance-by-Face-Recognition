package com.graduationproject.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.graduationproject.Model.ModeratorGetStudentsResponse
import com.graduationproject.Repository.ModeratorHomeRepository
import org.koin.android.ext.android.get

class ModeratorHomeViewModel (application: Application) : AndroidViewModel(application)
{
    private val repository = application.get<ModeratorHomeRepository>()

    val allStudents : LiveData<List<ModeratorGetStudentsResponse>>

    init {
        allStudents = repository.allStudents
    }

    fun RequestAllStudents()
    {
        repository.getAllStudents()
    }

    fun getallStudents() : LiveData<List<ModeratorGetStudentsResponse>> = allStudents
}