package com.graduationproject.Fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.graduationproject.Model.AdminRegisterModel

import com.graduationproject.R
import com.graduationproject.ViewModel.AdminViewModel
import com.graduationproject.ViewModelFactory.AdminViewModelFactory
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.fragment_admin_home.*
import org.koin.android.ext.android.get


class AdminHome : Fragment() {

    private var role = "moderator"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usersSpinner : MaterialSpinner = view.findViewById(R.id.admin_spinner)

        usersSpinner.setItems("مشرف","دكتور")

        val factory = get<AdminViewModelFactory>()


        val viewmodel =  ViewModelProvider(this , factory).get(AdminViewModel::class.java)


        usersSpinner.setOnItemSelectedListener{ view, position, id, item ->

            role = when(position)
            {
                0 -> "moderator"
                1 -> "doctor"
                else -> "moderator"
            }

        }

        viewmodel.getRegister().observe(viewLifecycleOwner , Observer {

            if(it.equals("successful"))
            {
                Toast.makeText(context,it,Toast.LENGTH_LONG).show()
                admin_nameedittext.editText?.setText("")
                admin_emailedittext.editText?.setText("")
                admin_passwordedittext.editText?.setText("")
            }
            else
                Toast.makeText(context,it,Toast.LENGTH_LONG).show()
        })


        admin_registerbt.setOnClickListener {

            val name = admin_nameedittext.editText?.text.toString()
            val email = admin_emailedittext.editText?.text.toString()
            val password = admin_passwordedittext.editText?.text.toString()

            viewmodel.userRegister(AdminRegisterModel(name,email,password,role))
        }
    }
}
