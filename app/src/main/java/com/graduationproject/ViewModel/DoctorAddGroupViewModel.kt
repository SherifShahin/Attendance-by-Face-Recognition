package com.graduationproject.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.graduationproject.Repository.DoctorAddGroupRepository
import org.koin.android.ext.android.get

class DoctorAddGroupViewModel (application: Application) : AndroidViewModel(application)
{
    val repository = application.get<DoctorAddGroupRepository>()

    val Added : LiveData<Boolean>

    val ErrorMessage : LiveData<String>

    init {
        Added = repository.Added
        ErrorMessage = repository.ErrorMessage
    }


    fun GroupAdded() : LiveData<Boolean>
    {
        return Added
    }


    fun geterrorMessage() : LiveData<String>
    {
        return ErrorMessage
    }


    fun AddGroup(groupName : String)
    {
        repository.AddGroup(groupName)
    }

}
