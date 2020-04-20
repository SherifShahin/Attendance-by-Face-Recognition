package com.graduationproject.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.graduationproject.Adapter.ModeratorAllStudentAdapter

import com.graduationproject.R
import com.graduationproject.ViewModel.ModeratorHomeViewModel
import com.graduationproject.ViewModelFactory.ModeratorHomeViewModelFactory
import kotlinx.android.synthetic.main.fragment_moderator_home.*
import org.koin.android.ext.android.get


class ModeratorHome : Fragment() {

    private lateinit var model : ModeratorHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moderator_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = get<ModeratorHomeViewModelFactory>()

        model = ViewModelProvider(this, factory).get(ModeratorHomeViewModel::class.java)

        model.RequestAllStudents()

        model.getallStudents().observe(viewLifecycleOwner , Observer {
            it?.let {

                if(!it.isEmpty())
                {
                    progressBar.visibility = View.GONE
                    moderatorallstudentrecycleview.visibility = View.VISIBLE
                }

                val adapter = ModeratorAllStudentAdapter(it)

                val layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                moderatorallstudentrecycleview.adapter = adapter

                moderatorallstudentrecycleview.layoutManager = layoutManager
            }
        })


        moderatorHome_toolbar.setOnMenuItemClickListener {

            when(it.itemId)
            {
                R.id.moderator_add_Student -> {
                    GoToDestination()
                    true
                }

                else -> false
            }
        }
    }

    fun GoToDestination()
    {
        val action = ModeratorHomeDirections.moderatorSetStudentDestination()

        NavHostFragment.findNavController(this).navigate(action)
    }

}
