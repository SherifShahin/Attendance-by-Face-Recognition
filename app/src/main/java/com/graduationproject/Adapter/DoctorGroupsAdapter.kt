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
import com.graduationproject.Fragment.DoctorHomeDirections
import com.graduationproject.R



class DoctorGroupsAdapter(var context: Context
                          , var list: List<DoctorGroups>)
    : RecyclerView.Adapter<DoctorGroupsAdapter.GroupsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder
    {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.doctor_group_item, parent, false)
        return GroupsViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {

        val group = list.get(position)

        holder.bind(group )
    }

    override fun getItemCount(): Int
    {
        return this.list.size
    }

    class GroupsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var groupName : TextView = itemView.findViewById(R.id.doctor_group_item_name)

        fun bind(group : DoctorGroups)
        {
            groupName.setText(group.groupName)

            itemView.setOnClickListener{ view ->
                val action = DoctorHomeDirections.doctorGroupStudents(group.groupId ,group.groupName)

                view.findNavController().navigate(action)
                }
        }
    }
}
