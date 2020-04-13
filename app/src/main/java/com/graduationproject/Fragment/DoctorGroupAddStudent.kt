package com.graduationproject.Fragment

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.graduationproject.Adapter.DoctorGroupAddStudentAdapter

import com.graduationproject.R
import com.graduationproject.ViewModel.DoctorGroupAddStudentViewModel
import com.graduationproject.ViewModelFactory.DoctorGroupAddStudentViewModelFactory
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.doctor_group_add_student_fragment.*
import org.koin.android.ext.android.get

class DoctorGroupAddStudent : Fragment() {

    private lateinit var searchview : MaterialSearchView

    private lateinit var toolbar : Toolbar

    private lateinit var model : DoctorGroupAddStudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.doctor_group_add_student_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val groupId  = arguments?.getString("gpid")

        searchview = view.findViewById(R.id.doctor_group_add_student_search_view)
        toolbar = view.findViewById(R.id.doctor_group_add_student_toolbar)

        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        toolbar.setTitle("Search")
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"))

        setHasOptionsMenu(true)

        val factory = get<DoctorGroupAddStudentViewModelFactory>()

        model = ViewModelProvider(this, factory).get(DoctorGroupAddStudentViewModel::class.java)

        model.getSearchResult().observe(viewLifecycleOwner , Observer {

            val adapter = DoctorGroupAddStudentAdapter(activity?.application!! , it , groupId!!)

            doctor_group_add_student_recycleview.adapter = adapter

            val layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            doctor_group_add_student_recycleview.layoutManager = layoutManager
        })


        model.Added().observe(viewLifecycleOwner , Observer {
            if(it){
                Toast.makeText(context!!,"added", Toast.LENGTH_LONG).show()
            }
        })


        searchview.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if(!newText.isEmpty()) {
                    model.search(newText)
                }
                return true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val menuItem = menu.findItem(R.id.action_search)
        searchview.setMenuItem(menuItem)
    }
}
