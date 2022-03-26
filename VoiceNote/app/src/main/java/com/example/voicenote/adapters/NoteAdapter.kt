package com.example.voicenote.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.voicenote.databinding.ListItemBinding
import com.example.voicenote.models.Note

class NoteAdapter(private val clickListener: NoteListener):  ListAdapter<Note,
        NoteAdapter.NoteViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.path == newItem.path
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.name == newItem.name
        }

    }

    class NoteViewHolder(private var binding:
                         ListItemBinding
    ):
        RecyclerView.ViewHolder(binding.root) {
        fun bind (clickListener: NoteListener, note: Note) {
            binding.note = note
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(ListItemBinding.inflate(
            LayoutInflater.from(parent.context)
        ))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

}

class NoteListener(val clickListener: (note: Note) -> Unit) {
    fun onClick(note: Note) = clickListener(note)
}
