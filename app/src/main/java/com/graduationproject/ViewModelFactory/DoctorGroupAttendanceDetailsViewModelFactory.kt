package com.graduationproject.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.graduationproject.ViewModel.DoctorGroupAttendanceDetailsViewModel

class DoctorGroupAttendanceDetailsViewModelFactory (private val application: Application): ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DoctorGroupAttendanceDetailsViewModel(
            application
        ) as T
    }
}