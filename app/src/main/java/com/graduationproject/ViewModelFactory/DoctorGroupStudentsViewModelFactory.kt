package com.graduationproject.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.graduationproject.ViewModel.DoctorGroupStudentsViewModel
import com.graduationproject.ViewModel.DoctorHomeViewModel

class DoctorGroupStudentsViewModelFactory (private val application: Application): ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DoctorGroupStudentsViewModel(application) as T
    }
}