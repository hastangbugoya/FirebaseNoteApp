package com.example.myfirebaseapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myfirebaseapplication.R
import com.example.myfirebaseapplication.model.Note
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePageActivity : AppCompatActivity() {
    private val notesAdapter = NotesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        notes_recyclerview.adapter = notesAdapter

        FirebaseDatabase.getInstance().reference.child("Notes")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userNotes = mutableListOf<Note>()
                    snapshot.children.forEach {
                        //this is the process when retrieving data from snapshot
                        // notes@ Misc I
                        it.getValue(Note::class.java)?.let { item ->
                            userNotes.add(item)
                        }
                    }
                    //send the updated list to adapter
                    notesAdapter.notes = userNotes
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })

        add_note_imageview.setOnClickListener {
            val fragment = UploadFragment()
            supportFragmentManager.beginTransaction()
                .addToBackStack(fragment.tag)
                .replace(R.id.main_frame, fragment)
                .commit()
        }
    }
}