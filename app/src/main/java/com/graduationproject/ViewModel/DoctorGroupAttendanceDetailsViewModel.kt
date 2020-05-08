package com.graduationproject.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.graduationproject.Model.DoctorStudentsSearchResponse
import com.graduationproject.Repository.DoctorGroupAttendanceDetailsRepository
import org.koin.android.ext.android.get

class DoctorGroupAttendanceDetailsViewModel (application: Application) : AndroidViewModel(application)  {

    private val repository  = application.get<DoctorGroupAttendanceDetailsRepository>()

    val AttendanceList : LiveData<List<DoctorStudentsSearchResponse>>

    val requestResult : LiveData<String>

    init {
        AttendanceList = repository.AttendanceList
        requestResult = repository.requestResult
    }

    fun getattendanceList() : LiveData<List<DoctorStudentsSearchResponse>> = AttendanceList

    fun getAbsent(groupId : String , attendanceId : String) = repository.getAbsent(groupId , attendanceId)

    fun getAttendees(groupId : String , attendanceId : String) = repository.getAttendees(groupId , attendanceId)

    fun getrequestResult() : LiveData<String> = requestResult

}
