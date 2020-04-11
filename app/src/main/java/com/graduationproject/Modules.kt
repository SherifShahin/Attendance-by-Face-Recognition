package com.graduationproject

import com.com.graduationproject.Data.AppDataBase
import com.graduationproject.Dao.Dao
import com.graduationproject.Repository.AdminRepository
import com.graduationproject.Repository.DoctorGroupStudentsRepository
import com.graduationproject.Repository.DoctorHomeRepository
import com.graduationproject.Repository.LoginRepository
import com.graduationproject.ViewModelFactory.AdminViewModelFactory
import com.graduationproject.ViewModelFactory.DoctorGroupStudentsViewModelFactory
import com.graduationproject.ViewModelFactory.DoctorHomeViewModelFactory
import com.graduationproject.ViewModelFactory.LoginViewModelFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModules = module {

     single { AppDataBase.invoke(androidApplication()).getUserDao() }

     single { LoginRepository(get()) }

     factory { LoginViewModelFactory(androidApplication()) }

     single { AdminRepository(get()) }

     factory { AdminViewModelFactory(androidApplication()) }

     single { DoctorHomeRepository(get()) }

     factory { DoctorHomeViewModelFactory(androidApplication()) }

     factory {(dao : Dao, id : String ) -> DoctorGroupStudentsRepository(dao,id) }

     factory { DoctorGroupStudentsViewModelFactory(androidApplication()) }
}
