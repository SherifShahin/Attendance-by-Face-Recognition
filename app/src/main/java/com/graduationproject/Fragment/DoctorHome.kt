package com.graduationproject.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.graduationproject.Adapter.DoctorGroupsAdapter

import com.graduationproject.R
import com.graduationproject.ViewModel.DoctorHomeViewModel
import com.graduationproject.ViewModelFactory.DoctorHomeViewModelFactory
import kotlinx.android.synthetic.main.fragment_doctor_home.*
import org.koin.android.ext.android.get

/**
 * A simple [Fragment] subclass.
 */
class DoctorHome : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = get<DoctorHomeViewModelFactory>()

        val model = ViewModelProvider(this, factory).get(DoctorHomeViewModel::class.java)

        // request for new groups
        model.RequestGroups()

        // display groups
        model.getGroups().observe(viewLifecycleOwner  , Observer {

            val adapter = DoctorGroupsAdapter(context!!,it)

            doctorHome_recycleview.adapter = adapter

            val layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            doctorHome_recycleview.layoutManager = layoutManager
        })


        doctorHome_toolbar.setOnMenuItemClickListener {
            when(it.itemId)
            {
                R.id.doctor_add_group -> {
                    GoToAddDestination()
                    true
                }
                else -> false
            }
        }

    }

    fun GoToAddDestination()
    {
        val action = DoctorHomeDirections.doctorAddGroupDestination()

        findNavController(this).navigate(action)
    }


}
