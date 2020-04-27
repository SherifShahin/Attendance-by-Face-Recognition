package com.graduationproject.Fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.graduationproject.FileUtil
import com.graduationproject.R
import com.graduationproject.ViewModel.ModeratorAddStudentViewModel
import com.graduationproject.ViewModel.ModeratorHomeViewModel
import com.graduationproject.ViewModelFactory.ModeratorAddStudentViewModelFactory
import com.graduationproject.ViewModelFactory.ModeratorHomeViewModelFactory
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_moderator_set_student.*
import org.koin.android.ext.android.get
import java.io.File


/**
 * A simple [Fragment] subclass.
 */
class ModeratorSetStudent : Fragment() {

    val READ_REQUEST_CODE = 0

    private lateinit var videoUri : Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moderator_set_student, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = get<ModeratorAddStudentViewModelFactory>()

        val viewmodel = ViewModelProvider(this , factory)
            .get(ModeratorAddStudentViewModel::class.java)

        viewmodel.getSubmitStatus().observe(viewLifecycleOwner , Observer {
            Toast.makeText(context!! , it , Toast.LENGTH_LONG).show()

            if(it.equals("Upload completed"))
            {
                moderator_add_student_name.editText?.setText("")
                moderator_add_student_email.editText?.setText("")
                moderator_add_student_department.editText?.setText("")
            }
            else
            {
                Toast.makeText(context!!,it,Toast.LENGTH_LONG).show()
            }

        })

        moderator_add_Student_submit.setOnClickListener {

            val name = moderator_add_student_name.editText?.text.toString()
            val email = moderator_add_student_email.editText?.text.toString()
            val department = moderator_add_student_department.editText?.text.toString()
            val video_uri = videoUri

            viewmodel.addNewStudent(name , email , department , video_uri)
        }


        moderator_add_Student_video.setOnClickListener {
            SelectStuedentVideo()
        }
    }

    private fun SelectStuedentVideo() {

        Dexter.withContext(context)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                }
            }).check()

        performFileSearch()
    }


    fun performFileSearch() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        intent.type = "video/*"
        startActivityForResult(intent, READ_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            if (data != null) {
                videoUri = data.getData()
                moderator_add_Student_video_link.setText(videoUri.path)
            }
        }
    }
}
