package com.graduationproject.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.graduationproject.Adapter.DoctorGroupStudentAdapter

import com.graduationproject.R
import com.graduationproject.ViewModel.DoctorGroupStudentsViewModel
import com.graduationproject.ViewModelFactory.DoctorGroupStudentsViewModelFactory
import kotlinx.android.synthetic.main.doctor_group_students_fragment.*
import org.koin.android.ext.android.get

class DoctorGroupStudents : Fragment() {

    private lateinit var model: DoctorGroupStudentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.doctor_group_students_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val groupId  = arguments?.getString("groupid")

        val groupname = arguments?.getString("groupname")

        doctorgroupstudents_toolbar.setTitle(groupname)

        val factory = get<DoctorGroupStudentsViewModelFactory>()

        model = ViewModelProvider(this, factory).get(DoctorGroupStudentsViewModel::class.java)

        model.init(groupId!!)

        model.getStudents().observe(viewLifecycleOwner , Observer {

            val adapter = DoctorGroupStudentAdapter(context!! , it)

            doctor_group_student_recycleview.adapter = adapter

            val layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            doctor_group_student_recycleview.layoutManager = layoutManager
        })

        doctorgroupstudents_toolbar.setOnMenuItemClickListener {
            when(it.itemId)
            {
                R.id.doctor_edit_students -> {

                    GoToEditDestination(groupId , groupname!!)

                    true
                }

                R.id.doctor_Attendance -> {
                    GoToAttendanceDestination(groupId,groupname!!)
                    true
                }

                else -> false
            }
        }
    }

    fun GoToEditDestination(groupid : String , groupname : String)
    {
        val action = DoctorGroupStudentsDirections.actionDoctorGroupStudentsToDoctorEditStudents(groupid , groupname)

        findNavController(this).navigate(action)
    }

    fun GoToAttendanceDestination(groupid : String , groupname : String)
    {
        val action = DoctorGroupStudentsDirections.actionDoctorGroupStudentsToDoctorAttendance(groupid , groupname)

        findNavController(this).navigate(action)
    }
}
