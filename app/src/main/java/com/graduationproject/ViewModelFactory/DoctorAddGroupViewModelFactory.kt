package com.graduationproject.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.graduationproject.ViewModel.DoctorAddGroupViewModel
import com.graduationproject.ViewModel.DoctorGroupAddStudentViewModel

class DoctorAddGroupViewModelFactory (private val application: Application): ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DoctorAddGroupViewModel(application) as T
    }
}