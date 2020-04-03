package com.graduationproject

import com.chatApp.Data.AppDataBase
import com.graduationproject.Repository.AdminRepository
import com.graduationproject.Repository.LoginRepository
import com.graduationproject.ViewModelFactory.AdminViewModelFactory
import com.graduationproject.ViewModelFactory.LoginViewModelFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModules = module {

     single { AppDataBase.invoke(androidApplication()).getUserDao() }

     single { LoginRepository(get()) }

     factory { LoginViewModelFactory(androidApplication()) }

     single {AdminRepository(get()) }

     factory { AdminViewModelFactory(androidApplication()) }
}
