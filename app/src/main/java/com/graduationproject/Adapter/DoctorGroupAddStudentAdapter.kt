package com.graduationproject.Adapter

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.graduationproject.DatabaseModel.DoctorGroups
import com.graduationproject.DatabaseModel.DoctorStudents
import com.graduationproject.Fragment.DoctorHomeDirections
import com.graduationproject.Model.DoctorStudentsSearchResponse
import com.graduationproject.R
import com.graduationproject.Repository.DoctorGroupAddStudentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get


class DoctorGroupAddStudentAdapter(var application: Application
                                   , var list: List<DoctorStudentsSearchResponse>
                                   ,val groupId : String
) : RecyclerView.Adapter<DoctorGroupAddStudentAdapter.StudentsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentsViewHolder
    {
       val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.doctor_group_add_student_item, parent, false)
        return StudentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentsViewHolder, position: Int) {

        val student = list.get(position)

        holder.bind(application , student , groupId)
    }

    override fun getItemCount(): Int
    {
        return this.list.size
    }

    class StudentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var studentName : TextView = itemView.findViewById(R.id.doctor_group_add_student_name)
        var studentDepartment :TextView = itemView.findViewById(R.id.doctor_group_add_student_department)

        fun bind(application: Application , student : DoctorStudentsSearchResponse , groupId: String)
        {
            studentName.setText(student.studentName)

            studentDepartment.setText(student.department)

            itemView.setOnClickListener {
                val repository = application.get<DoctorGroupAddStudentRepository>()

                repository.addStudent(student , groupId)
            }
        }
    }
}
