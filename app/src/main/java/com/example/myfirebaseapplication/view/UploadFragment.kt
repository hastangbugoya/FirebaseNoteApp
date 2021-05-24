package com.example.myfirebaseapplication.view

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.myfirebaseapplication.R
import com.example.myfirebaseapplication.model.Note
import com.example.myfirebaseapplication.utility.DebugHelper.Companion.LogKitty
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.upload_layout.*
import java.util.*

class UploadFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.upload_layout, container, false)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upload_button.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().reference.child("Notes")
            val key = ref.push().key ?: ""
            val nowDate: Date
            val post = Note(
                FirebaseAuth.getInstance().currentUser.toString(),
                key,
                note_upload_note.text.toString().trim()
            )
            LogKitty(">$${key}< >>>>> ${post.toString()}")
            ref.child(key).setValue(post)
            Toast.makeText(requireContext(), "Success!!", Toast.LENGTH_SHORT).show()
        }
    }
}