package com.graduationproject.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.graduationproject.DatabaseModel.DoctorGroups
import com.graduationproject.DatabaseModel.DoctorGroupsStudentsRelation
import com.graduationproject.DatabaseModel.DoctorStudents
import com.graduationproject.DatabaseModel.User
import retrofit2.http.DELETE


@Dao
interface Dao
{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setUser(user : User) : Long

    @Query("SELECT * FROM user")
    fun getUser() : User

    @Query("DELETE FROM USER")
    fun DeleteUser()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setDoctorGroup(group : DoctorGroups)


    @Query("SELECT * FROM DoctorGroups WHERE group_id = :id")
    fun getDoctorGroup(id : String) : DoctorGroups


    @Query("SELECT * FROM DoctorGroups")
    fun getDoctorGroup() : LiveData<List<DoctorGroups>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setDoctorStudent(student : DoctorStudents , relation : DoctorGroupsStudentsRelation)

    @Query("SELECT * FROM DoctorStudents NATURAL JOIN (SELECT student_id FROM DOCTORGROUPSSTUDENTSRELATION WHERE group_id = :id)")
    fun getGroupStudents(id : String) : LiveData<List<DoctorStudents>>

    @Query("DELETE FROM DOCTORGROUPSSTUDENTSRELATION")
    fun deleteRelations()
}