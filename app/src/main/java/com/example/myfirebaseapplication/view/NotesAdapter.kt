package com.example.myfirebaseapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirebaseapplication.R
import com.example.myfirebaseapplication.model.Note
import kotlinx.android.synthetic.main.notes_item_layout.view.*

class NotesAdapter(): RecyclerView.Adapter<NotesAdapter.PostViewHolder>() {

    var notes: List<Note> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView  = LayoutInflater.from(parent.context).inflate(
            R.layout.notes_item_layout, parent, false
        )
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = notes[position]
        holder.apply {
            itemView.notes_item_note_textview.text = post.note
        }
    }


    override fun getItemCount(): Int = notes.size
}