package com.graduationproject.Fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.graduationproject.Dao.Dao
import com.graduationproject.Model.LoginModel
import com.graduationproject.R
import com.graduationproject.ViewModel.LoginViewModel
import com.graduationproject.ViewModelFactory.LoginViewModelFactory
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get


/**
 * A simple [Fragment] subclass.
 */
class Login : Fragment()
{
    private var role = "admin"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
            checkLogin()
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usersSpinner : MaterialSpinner = view.findViewById(R.id.spinner)

        usersSpinner.setItems("ادمن","مشرف","دكتور")

        val factory  = get<LoginViewModelFactory>()

        val model = ViewModelProvider(this , factory).get(LoginViewModel::class.java)

        usersSpinner.setOnItemSelectedListener{ view, position, id, item ->

            role = when(position)
            {
                0 -> "admin"
                1 -> "moderator"
                2 -> "doctor"
                else -> "admin"
            }

        }

        model.getLogin().observe(viewLifecycleOwner , Observer {
            it?.let {
                if(it.equals("successful"))
                {
                    GoToDestination(role)
                }
                else
                    Log.e("login",it)
            }
        })


        login_loginbt.setOnClickListener {

            val email = login_email.editText?.text.toString()

            val password = login_password.editText?.text.toString()

            model.userLogin(LoginModel(email,password,role))
        }

    }

    private fun GoToDestination(role: String)
    {
        if(role.equals("admin"))
        {
            GoToAdminDestination()
        }
        else if(role.equals("moderator"))
        {
            GoToModeratorDestination()
        }
        else  if(role.equals("doctor"))
        {
            GoToDoctorDestination()
        }
    }

    private fun GoToDoctorDestination() {
        val action =
            LoginDirections.DoctorDestination()

        findNavController().navigate(action)
    }

    private fun GoToModeratorDestination() {
        val action =
            LoginDirections.moderatorDestination()

        findNavController().navigate(action)

    }

    private fun GoToAdminDestination() {
        val action =
            LoginDirections.adminDestination()

        findNavController(this).navigate(action)
    }

    private fun checkLogin() {

        CoroutineScope(Dispatchers.IO).launch {

            val dao = get<Dao>()

            val user = dao.getUser()

            CoroutineScope(Dispatchers.Main).launch {

                user?.let {

                    it.token?.let {

                        if (!it.isEmpty()) {
                            GoToDestination(user.role)
                        }
                    }
                }
            }
        }
    }
}
