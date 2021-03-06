package com.com.graduationproject.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.graduationproject.Dao.Dao
import com.graduationproject.DatabaseModel.DoctorGroups
import com.graduationproject.DatabaseModel.DoctorGroupsStudentsRelation
import com.graduationproject.DatabaseModel.DoctorStudents

import com.graduationproject.DatabaseModel.User

@Database(entities = [User::class
    ,DoctorGroups::class ,
    DoctorStudents::class ,
    DoctorGroupsStudentsRelation::class],
    version = 4
    ,exportSchema = false)

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