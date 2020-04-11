package com.graduationproject.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.graduationproject.DatabaseModel.DoctorGroups
import com.graduationproject.Repository.DoctorHomeRepository
import org.koin.android.ext.android.get

class DoctorHomeViewModel (application: Application) : AndroidViewModel(application)
{
    val repository = application.get<DoctorHomeRepository>()
    private val groups : LiveData<List<DoctorGroups>>

     init {
         groups = repository.groups
     }

    fun getGroups() : LiveData<List<DoctorGroups>>
    {
        return groups
    }

    fun RequestGroups()
    {
        repository.getGroups()
    }
}