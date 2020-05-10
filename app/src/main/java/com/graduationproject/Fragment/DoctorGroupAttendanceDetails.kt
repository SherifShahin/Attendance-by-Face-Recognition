package com.graduationproject.Fragment

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.graduationproject.Adapter.DoctorGroupAttendanceDetailsAdapter
import com.graduationproject.R
import com.graduationproject.ViewModel.DoctorGroupAttendanceDetailsViewModel
import com.graduationproject.ViewModelFactory.DoctorGroupAttendanceDetailsViewModelFactory
import kotlinx.android.synthetic.main.doctor_group_attendance_details_fragment.*
import org.koin.android.ext.android.get


class DoctorGroupAttendanceDetails : Fragment() {

    private lateinit var viewModel: DoctorGroupAttendanceDetailsViewModel

    private lateinit var GroupId : String

    private lateinit var AttendanceId : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.doctor_group_attendance_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val groupId  = arguments?.getString("gpid")
        val attendanceId  = arguments?.getString("atncid")

        GroupId = groupId!!
        AttendanceId = attendanceId!!

        val factory = get<DoctorGroupAttendanceDetailsViewModelFactory>()

        viewModel = ViewModelProviders.of(this , factory).get(DoctorGroupAttendanceDetailsViewModel::class.java)

        viewModel.getAttendees(groupId!! , attendanceId!!)

        doctor_Attendance_details_toolbar.title = "Attendees"

        viewModel.getattendanceList().observe(viewLifecycleOwner , Observer {
            it?.let {

                ProgressBarGONE()

                RecyclerViewVISIBLE()

                val adapter = DoctorGroupAttendanceDetailsAdapter(it)

                doctor_Attendance_details_recyclerView.adapter = adapter

                val layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                doctor_Attendance_details_recyclerView.layoutManager = layoutManager
            }
        })

        viewModel.getrequestResult().observe(viewLifecycleOwner , Observer {
            ProgressBarGONE()
            it?.let {
                Toast.makeText(context!! , it , Toast.LENGTH_LONG).show()
            }
        })

        doctor_Attendance_details_toolbar.setOnMenuItemClickListener {

            when(it.itemId){

                R.id.doctor_Attendance_Attendees ->{
                    ProgressBarVISIBLE()
                    RecyclerViewGONE()
                    doctor_Attendance_details_toolbar.title = "Attendees"
                    viewModel.getAttendees(groupId,attendanceId)
                    true
                }

                R.id.doctor_Attendance_Absent ->{
                    ProgressBarVISIBLE()
                    RecyclerViewGONE()
                    doctor_Attendance_details_toolbar.title = "Absent"
                    viewModel.getAbsent(groupId,attendanceId)
                    true
                }

                else -> false
            }
        }

        doctor_Attendance_details_floating_action_button.setOnClickListener {

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            startActivityForResult(intent , 0)
        }
    }

    fun ProgressBarVISIBLE(){
        doctor_Attendance_details_progressBar.visibility = View.VISIBLE
    }

    fun ProgressBarGONE(){
        doctor_Attendance_details_progressBar.visibility = View.GONE
    }


    fun RecyclerViewVISIBLE()
    {
        doctor_Attendance_details_recyclerView.visibility = View.VISIBLE
    }

    fun RecyclerViewGONE()
    {
        doctor_Attendance_details_recyclerView.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val photo = data?.extras?.get("data") as Bitmap

        val uri =
            getImageUri(context!!, photo)

        Log.e("uri" , uri.toString())
        viewModel.MakeStudentAttendance(uri!! ,GroupId , AttendanceId)

    }

    fun getImageUri(inContext: Context, inImage: Bitmap?): Uri? {
        val OutImage = Bitmap.createScaledBitmap(inImage, 1000, 1000, true)
        val path = Images.Media.insertImage(
            inContext.contentResolver,
            OutImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }
}
