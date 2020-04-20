package com.graduationproject

import com.com.graduationproject.Data.AppDataBase
import com.graduationproject.Dao.Dao
import com.graduationproject.Repository.*
import com.graduationproject.ViewModelFactory.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModules = module {

     single { AppDataBase.invoke(androidApplication()).getUserDao() }

     single { LoginRepository(get()) }

     factory { LoginViewModelFactory(androidApplication()) }

     single { AdminRepository(get()) }

     factory { AdminViewModelFactory(androidApplication()) }

     factory { DoctorHomeRepository(get()) }

     factory { DoctorHomeViewModelFactory(androidApplication()) }

     factory {(dao : Dao, id : String ) -> DoctorGroupStudentsRepository(dao,id) }

     factory { DoctorGroupStudentsViewModelFactory(androidApplication()) }

     single { DoctorGroupAddStudentRepository(get()) }

     factory { DoctorGroupAddStudentViewModelFactory(androidApplication())}

     factory { DoctorAddGroupRepository(get()) }

     factory { DoctorAddGroupViewModelFactory(androidApplication()) }

     single { ModeratorHomeRepository(get()) }

     factory { ModeratorHomeViewModelFactory(androidApplication()) }

}
