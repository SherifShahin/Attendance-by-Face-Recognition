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
import com.graduationproject.Model.ModeratorGetStudentsResponse
import com.graduationproject.R
import com.graduationproject.Repository.DoctorGroupAddStudentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get


class ModeratorAllStudentAdapter( var list: List<ModeratorGetStudentsResponse>
) : RecyclerView.Adapter<ModeratorAllStudentAdapter.StudentsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentsViewHolder
    {
       val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.doctor_group_add_student_item, parent, false)
        return StudentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentsViewHolder, position: Int) {

        val student = list.get(position)

        holder.bind( student)
    }

    override fun getItemCount(): Int
    {
        return this.list.size
    }

    class StudentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var studentName : TextView = itemView.findViewById(R.id.doctor_group_add_student_name)
        var studentDepartment :TextView = itemView.findViewById(R.id.doctor_group_add_student_department)

        fun bind(student : ModeratorGetStudentsResponse)
        {
            studentName.setText(student.studentName)

            studentDepartment.setText(student.department)
        }
    }
}
