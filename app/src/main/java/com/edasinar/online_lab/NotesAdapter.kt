package com.edasinar.online_lab

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edasinar.model.Notes
import com.edasinar.online_lab.databinding.CardLayoutBinding

class NotesAdapter(private val notesList: ArrayList<Notes>): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val rowBinding: CardLayoutBinding =
            CardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(rowBinding)
    }

    override fun onBindViewHolder(holder: NotesAdapter.NotesViewHolder, position: Int) {
        holder.binding.titleName.text = notesList[position].fileName
        val clickListener = View.OnClickListener {
            val intent = Intent(holder.itemView.context, OneLessonActivity::class.java)
            intent.putExtra("url",notesList[position].url)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.mainCardView.setOnClickListener(clickListener)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class NotesViewHolder(binding: CardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal val binding: CardLayoutBinding

        init {
            this.binding = binding
        }
    }
}