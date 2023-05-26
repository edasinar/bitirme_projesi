package com.edasinar.online_lab

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edasinar.model.Lessons
import com.edasinar.online_lab.databinding.CardLayoutBinding

class LessonsAdapter(private val lessonList: ArrayList<Lessons>):
RecyclerView.Adapter<LessonsAdapter.LessonsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonsViewHolder {
        val rowBinding: CardLayoutBinding =
            CardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LessonsViewHolder(rowBinding)
    }

    override fun onBindViewHolder(holder: LessonsViewHolder, position: Int) {
        holder.binding.titleName.text = lessonList[position].fileName
        val clickListener = View.OnClickListener {
                val intent = Intent(holder.itemView.context, OneLessonActivity::class.java)
                intent.putExtra("url",lessonList[position].url)
                holder.itemView.context.startActivity(intent)
        }
        holder.binding.mainCardView.setOnClickListener(clickListener)
    }

    override fun getItemCount(): Int {
        return lessonList.size
    }

    inner class LessonsViewHolder(binding: CardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal val binding: CardLayoutBinding

        init {
            this.binding = binding
        }
    }

}