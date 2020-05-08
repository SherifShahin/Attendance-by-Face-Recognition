package com.graduationproject.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graduationproject.Adapter.DoctorGroupAttendanceDetailsAdapter

import com.graduationproject.R
import com.graduationproject.ViewModel.DoctorGroupAttendanceDetailsViewModel
import com.graduationproject.ViewModelFactory.DoctorGroupAttendanceDetailsViewModelFactory
import kotlinx.android.synthetic.main.doctor_group_attendance_details_fragment.*
import org.koin.android.ext.android.get

class DoctorGroupAttendanceDetails : Fragment() {

    private lateinit var viewModel: DoctorGroupAttendanceDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.doctor_group_attendance_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val groupId  = arguments?.getString("gpid")
        val attendanceId  = arguments?.getString("atncid")

        val factory = get<DoctorGroupAttendanceDetailsViewModelFactory>()

        viewModel = ViewModelProviders.of(this , factory).get(DoctorGroupAttendanceDetailsViewModel::class.java)

        viewModel.getAttendees(groupId!! , attendanceId!!)

        doctor_Attendance_details_toolbar.title = "Attendees"

        viewModel.getattendanceList().observe(viewLifecycleOwner , Observer {
            it?.let {

                ProgressBarGONE()

                RecyclerViewVISIBLE()

                val adapter = DoctorGroupAttendanceDetailsAdapter(it)

                doctor_Attendance_details_recyclerView.adapter = adapter

                val layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                doctor_Attendance_details_recyclerView.layoutManager = layoutManager
            }
        })

        viewModel.getrequestResult().observe(viewLifecycleOwner , Observer {
            ProgressBarGONE()
            it?.let {
                Toast.makeText(context!! , it , Toast.LENGTH_LONG).show()
            }
        })

        doctor_Attendance_details_toolbar.setOnMenuItemClickListener {

            when(it.itemId){

                R.id.doctor_Attendance_Attendees ->{
                    ProgressBarVISIBLE()
                    RecyclerViewGONE()
                    doctor_Attendance_details_toolbar.title = "Attendees"
                    viewModel.getAttendees(groupId,attendanceId)
                    true
                }

                R.id.doctor_Attendance_Absent ->{
                    ProgressBarVISIBLE()
                    RecyclerViewGONE()
                    doctor_Attendance_details_toolbar.title = "Absent"
                    viewModel.getAbsent(groupId,attendanceId)
                    true
                }

                else -> false
            }
        }
    }

    fun ProgressBarVISIBLE(){
        doctor_Attendance_details_progressBar.visibility = View.VISIBLE
    }

    fun ProgressBarGONE(){
        doctor_Attendance_details_progressBar.visibility = View.GONE
    }


    fun RecyclerViewVISIBLE()
    {
        doctor_Attendance_details_recyclerView.visibility = View.VISIBLE
    }

    fun RecyclerViewGONE()
    {
        doctor_Attendance_details_recyclerView.visibility = View.GONE
    }
}
