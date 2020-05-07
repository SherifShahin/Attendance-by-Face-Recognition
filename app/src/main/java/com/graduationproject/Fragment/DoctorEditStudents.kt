package com.graduationproject.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.graduationproject.Adapter.DoctorGroupStudentAdapter

import com.graduationproject.R
import com.graduationproject.ViewModel.DoctorEditStudentsViewModel
import com.graduationproject.ViewModelFactory.DoctorEditStudentsViewModelFactory
import kotlinx.android.synthetic.main.doctor_edit_students_fragment.*
import org.koin.android.ext.android.get

class DoctorEditStudents : Fragment() {

    private lateinit var viewModel: DoctorEditStudentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.doctor_edit_students_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val groupId  = arguments?.getString("groupid")

        val groupname = arguments?.getString("groupname")

        doctoreditstudent_toolbar.setTitle(groupname+" - Edit")

        val factory = get<DoctorEditStudentsViewModelFactory>()

        viewModel = ViewModelProviders.of(this , factory).get(DoctorEditStudentsViewModel::class.java)

        viewModel.init(groupId!!)

        viewModel.getStudents().observe(viewLifecycleOwner , Observer {

            val adapter = DoctorGroupStudentAdapter(context!!,it)
            doctoreditstudent_recyclerView.adapter = adapter

            val layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            doctoreditstudent_recyclerView.layoutManager = layoutManager
        })

        doctoreditstudent_toolbar.setOnMenuItemClickListener {

            when(it.itemId){
                R.id.doctor_group_add_student ->{
                    GoToAddStudent(groupId)
                    true
                }

                else -> false
            }
        }

    }

    private fun GoToAddStudent(groupId: String?) {
        val action = DoctorEditStudentsDirections.doctorGroupAddStudentDestination(groupId!!)
        findNavController().navigate(action)
    }
}
