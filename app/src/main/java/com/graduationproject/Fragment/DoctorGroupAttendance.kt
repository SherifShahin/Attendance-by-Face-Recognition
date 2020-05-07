package com.graduationproject.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.graduationproject.Adapter.DoctorGroupAttendanceAdapter

import com.graduationproject.R
import com.graduationproject.ViewModel.DoctorGroupAttendanceViewModel
import com.graduationproject.ViewModelFactory.DoctorGroupAttendanceViewModelFactory
import kotlinx.android.synthetic.main.doctor_group_attendance_fragment.*
import org.koin.android.ext.android.get

class DoctorGroupAttendance : Fragment() {

    private lateinit var viewModel :  DoctorGroupAttendanceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.doctor_group_attendance_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val groupId  = arguments?.getString("groupid")

        val groupname = arguments?.getString("groupname")

        doctor_Attendance_toolbar.title = "$groupname - Attendance"

        val factory = get<DoctorGroupAttendanceViewModelFactory>()

        viewModel = ViewModelProviders.of(this, factory).get(DoctorGroupAttendanceViewModel::class.java)

        viewModel.RequestGroupAttendace(groupId!!)

        viewModel.getGroupAttendance().observe(viewLifecycleOwner , Observer {

            Log.e("attendance", it.toString())

            doctor_Attendance_progressBar.visibility = View.GONE

            doctor_Attendance_recyclerView.visibility = View.VISIBLE

            val adapter = DoctorGroupAttendanceAdapter(context!! ,it)

            doctor_Attendance_recyclerView.adapter = adapter

            val layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            doctor_Attendance_recyclerView.layoutManager = layoutManager
        })

        viewModel.getRequestResult().observe(viewLifecycleOwner , Observer {
            Toast.makeText(context!! , it , Toast.LENGTH_LONG).show()
            doctor_Attendance_progressBar.visibility = View.GONE
        })

        doctor_Attendance_floating_action_button.setOnClickListener {
            viewModel.setNewAttendance(groupId)
            Toast.makeText(context!!,"sending.....",Toast.LENGTH_LONG).show()
        }
    }
}
