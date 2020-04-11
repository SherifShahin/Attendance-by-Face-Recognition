package com.graduationproject.Adapter

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
import com.graduationproject.R



class DoctorGroupStudentAdapter(var context: Context
                                ,var list: List<DoctorStudents>)
    : RecyclerView.Adapter<DoctorGroupStudentAdapter.StudentsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentsViewHolder
    {
       val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.doctor_group_student_item, parent, false)
        return StudentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentsViewHolder, position: Int) {

        val student = list.get(position)

        holder.bind(student)
    }

    override fun getItemCount(): Int
    {
        return this.list.size
    }

    class StudentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var studentName : TextView = itemView.findViewById(R.id.doctor_group_student_item_name)

        fun bind(student : DoctorStudents)
        {
            studentName.setText(student.name)


        }
    }
}
