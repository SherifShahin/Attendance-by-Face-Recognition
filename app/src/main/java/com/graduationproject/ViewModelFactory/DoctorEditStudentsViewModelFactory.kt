package com.graduationproject.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.graduationproject.ViewModel.DoctorEditStudentsViewModel
import com.graduationproject.ViewModel.DoctorGroupStudentsViewModel
import com.graduationproject.ViewModel.DoctorHomeViewModel

class DoctorEditStudentsViewModelFactory (private val application: Application): ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DoctorEditStudentsViewModel(application) as T
    }
}