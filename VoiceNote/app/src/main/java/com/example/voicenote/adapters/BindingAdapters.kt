package com.example.voicenote.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.voicenote.models.Note

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<Note>?) {
    val adapter = recyclerView.adapter as NoteAdapter
    adapter.submitList(data)
}