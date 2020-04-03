package com.chatApp.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.graduationproject.DatabaseModel.User


@Dao
interface Dao
{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setUser(user : User) : Long

    @Query("SELECT * FROM user")
    fun getUser() : User



}