package com.graduationproject.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.graduationproject.Model.DoctorStudentsSearchResponse
import com.graduationproject.Repository.DoctorGroupAddStudentRepository
import org.koin.android.ext.android.get


class DoctorGroupAddStudentViewModel (application: Application) : AndroidViewModel(application)
{
    val repository = application.get<DoctorGroupAddStudentRepository>()

    val searchresult : LiveData<List<DoctorStudentsSearchResponse>>

    val studentAdded : LiveData<Boolean>

    init {
        searchresult = repository.searchresult
        studentAdded = repository.StudentAdded
    }

    fun search(newText: String) {
        repository.serach(newText)
    }

    fun getSearchResult() : LiveData<List<DoctorStudentsSearchResponse>>
    {
        return searchresult
    }

    fun Added() : LiveData<Boolean>
    {
        return studentAdded
    }

}
