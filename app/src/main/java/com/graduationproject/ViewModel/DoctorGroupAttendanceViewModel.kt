package com.graduationproject.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.graduationproject.Model.DoctorGroupAttendanceResponse
import com.graduationproject.Repository.DoctorGroupAttendanceRepository
import org.koin.android.ext.android.get

class DoctorGroupAttendanceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = application.get<DoctorGroupAttendanceRepository>()

    private val groupAttendance : LiveData<List<DoctorGroupAttendanceResponse>>

    private var requestResult : LiveData<String>

    init {
        groupAttendance = repository.groupAttendance
        requestResult = repository.requestResult
    }

    fun getGroupAttendance() : LiveData<List<DoctorGroupAttendanceResponse>> = groupAttendance

    fun RequestGroupAttendace(groupId : String){
        repository.getGroupAttendace(groupId)
    }

    fun getRequestResult() : LiveData<String>{
        return requestResult
    }

    fun setNewAttendance(groupId: String) {
        repository.setNewAttendance(groupId)
    }
}
