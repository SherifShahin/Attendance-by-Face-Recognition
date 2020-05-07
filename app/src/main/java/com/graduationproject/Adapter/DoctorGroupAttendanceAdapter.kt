package com.graduationproject.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.graduationproject.Model.DoctorGroupAttendanceResponse
import com.graduationproject.R
import java.text.SimpleDateFormat

import java.util.*

class DoctorGroupAttendanceAdapter(var context: Context
                                   , var list: List<DoctorGroupAttendanceResponse>)
    : RecyclerView.Adapter<DoctorGroupAttendanceAdapter.AttedanceViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttedanceViewHolder
    {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.doctor_group_attendance_item, parent, false)
        return AttedanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttedanceViewHolder, position: Int) {

        val attedance = list.get(position)

        holder.bind(attedance)
    }

    override fun getItemCount(): Int
    {
        return this.list.size
    }

    class AttedanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var attendanceName : TextView = itemView.findViewById(R.id.doctor_attendance_item_name)

        fun bind(attendance : DoctorGroupAttendanceResponse)
        {
            val date = attendance.date

            //yyyy-mm-ddThh:MM:ssZ

            val df = SimpleDateFormat("dd/M/yyyy")

            val today = Date()

            val todayDate = df.format(today)

            if(date.equals(todayDate)){
                attendanceName.setText("Today")
            }
            else
                attendanceName.setText(date)

            itemView.setOnClickListener{ view ->
                // navigate to attendance
            }
        }
    }
}
