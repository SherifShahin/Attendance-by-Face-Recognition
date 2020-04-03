package com.graduationproject

import com.chatApp.Data.AppDataBase
import com.graduationproject.Repository.LoginRepository
import com.graduationproject.ViewModelFactory.LoginViewModelFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModules = module {

     single { AppDataBase.invoke(androidApplication()).getUserDao() }

     single { LoginRepository(get()) }

     factory { LoginViewModelFactory(androidApplication()) }

//     single {AppDataBase.invoke(androidApplication()).getUserDao()}
//
//     factory { (dao : Dao, id : String ) -> ChatRepository(dao,id) }

//     factory { ChatViewModelFactory(androidApplication()) }
//
//     single { ChatListRepository(get()) }
//
//     single { ConnectionsListRepository(get()) }
//
//     factory { ChatListViewModelFactory(androidApplication()) }
//
//     factory { ConnectionsListViewModelFactory(androidApplication()) }

}
