package com.graduationproject.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.graduationproject.Dao.Dao
import com.graduationproject.DatabaseModel.DoctorStudents
import com.graduationproject.Repository.DoctorEditStudentsRepository
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class DoctorEditStudentsViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var students: LiveData<List<DoctorStudents>>

    private val dao = application.get<Dao>()

    val app = application

    lateinit var repository : DoctorEditStudentsRepository

    fun init(id : String)
    {
        repository = app.get { parametersOf(dao,id) }
        students = repository.students
    }

    fun getStudents() : LiveData<List<DoctorStudents>> = students
}
