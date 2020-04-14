package com.graduationproject.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.graduationproject.R
import com.graduationproject.ViewModel.DoctorAddGroupViewModel
import com.graduationproject.ViewModelFactory.DoctorAddGroupViewModelFactory
import kotlinx.android.synthetic.main.doctor_add_group_fragment.*
import org.koin.android.ext.android.get

class DoctorAddGroup : Fragment() {

    private lateinit var viewModel: DoctorAddGroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.doctor_add_group_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = get<DoctorAddGroupViewModelFactory>()

        viewModel = ViewModelProvider(this, factory).get(DoctorAddGroupViewModel::class.java)

        viewModel.GroupAdded().observe(viewLifecycleOwner , Observer {
            if(it)
            {
                Toast.makeText(context!! ,"Added" ,Toast.LENGTH_LONG).show()
                doctor_add_group_edittext.editText?.setText("")
            }
        })

        viewModel.geterrorMessage().observe(viewLifecycleOwner , Observer {

            if(!it.isEmpty())
            {
                Toast.makeText(context!! ,it ,Toast.LENGTH_LONG).show()
            }
        })

        doctor_add_group_bt.setOnClickListener {

            val groupName = doctor_add_group_edittext.editText?.text.toString()

            if(!groupName.isEmpty())
            {
                Toast.makeText(context!!,"sending..",Toast.LENGTH_LONG).show()
                viewModel.AddGroup(groupName)
            }
        }
    }

}
