package com.edasinar.online_lab

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.edasinar.online_lab.databinding.RecMainRowBinding

class MainAdapter(var menuNames: ArrayList<String>) :
    RecyclerView.Adapter<MainAdapter.MainHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val rowBinding: RecMainRowBinding =
            RecMainRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(rowBinding)
    }

    override fun onBindViewHolder(holder: MainHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.menuName.setText(menuNames[position])
        holder.itemView.setOnClickListener {
            if (menuNames[position] == "Notlar") {
                val intent = Intent(holder.itemView.context, NotesActivity::class.java)
                holder.itemView.context.startActivity(intent)
            } else if (menuNames[position] == "Dersler") {
                val intent = Intent(holder.itemView.context, LessonsActivity::class.java)
                holder.itemView.context.startActivity(intent)
            } else if(menuNames[position] == "Mesajlar") {
                val intent = Intent(holder.itemView.context, MessagesActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }
        }
        val clickListener = View.OnClickListener {
            if (menuNames[position] == "Notlar") {
                val intent = Intent(holder.itemView.context, OneNoteActivity::class.java)
                holder.itemView.context.startActivity(intent)
            } else if (menuNames[position] == "Dersler") {
                val intent = Intent(holder.itemView.context, OneLessonActivity::class.java)
                holder.itemView.context.startActivity(intent)
            } else if(menuNames[position] == "Mesajlar") {
                val intent = Intent(holder.itemView.context, MessagesActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }
        }
        holder.binding.card1.setOnClickListener(clickListener)
        holder.binding.card2.setOnClickListener(clickListener)
        holder.binding.card3.setOnClickListener(clickListener)
    }

    override fun getItemCount(): Int {
        return menuNames.size
    }

    inner class MainHolder(binding: RecMainRowBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        internal val binding: RecMainRowBinding

        init {
            this.binding = binding
        }
    }
}
