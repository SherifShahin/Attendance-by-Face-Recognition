package com.graduationproject.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.graduationproject.ViewModel.AdminViewModel

import com.graduationproject.ViewModel.LoginViewModel
import com.graduationproject.ViewModel.ModeratorHomeViewModel

class ModeratorHomeViewModelFactory (private val application: Application): ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ModeratorHomeViewModel(application) as T
    }
}