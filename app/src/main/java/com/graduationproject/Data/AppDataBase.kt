package com.chatApp.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chatApp.Dao.Dao

import com.graduationproject.DatabaseModel.User

@Database(entities = [User::class], version = 1 ,exportSchema = false)

abstract class AppDataBase : RoomDatabase()
{
    abstract fun getUserDao() : Dao

    companion object{

        @Volatile
        private var instance : AppDataBase?= null

        private val Lock = Any()

        operator  fun invoke(context: Context) = instance ?: synchronized(Lock)
        {
            instance ?: buildDataBase(context)
        }

        fun buildDataBase(context : Context) =
            Room.databaseBuilder(context,
                AppDataBase::class.java
                ,"GraduationProject.db"
                ).fallbackToDestructiveMigration()
                .build()
    }
}