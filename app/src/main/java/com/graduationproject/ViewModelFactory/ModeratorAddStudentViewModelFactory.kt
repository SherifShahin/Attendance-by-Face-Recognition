package com.graduationproject.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.graduationproject.ViewModel.AdminViewModel

import com.graduationproject.ViewModel.LoginViewModel
import com.graduationproject.ViewModel.ModeratorAddStudentViewModel
import com.graduationproject.ViewModel.ModeratorHomeViewModel

class ModeratorAddStudentViewModelFactory (private val application: Application): ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ModeratorAddStudentViewModel(application) as T
    }
}