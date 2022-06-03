package com.example.wordledex.achievements

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wordledex.R


class AchievementAdapter(val achievementList: ArrayList<Achievement>): RecyclerView.Adapter<AchievementAdapter.AchievementHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AchievementHolder(layoutInflater.inflate(R.layout.item_achievement, parent, false))
    }

    override fun onBindViewHolder(holder: AchievementHolder, position: Int) {
        holder.render(achievementList[position])
    }

    override fun getItemCount(): Int = achievementList.size

    class AchievementHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun render(achievement : Achievement){
            val name = view.findViewById<TextView>(R.id.textViewAchievementName)
            val icon = view.findViewById<ImageView>(R.id.imageViewAchievement)
            val description = view.findViewById<TextView>(R.id.textViewAchievementDescription)

            if (achievement.got){
                icon.setImageResource(achievement.icon)
                name.text = achievement.name
                name.setTextColor(Color.parseColor("#FFFFFF"))
                description.text = achievement.description
                description.setTextColor(Color.parseColor("#FFFFFF"))
            } else {
                icon.setImageResource(R.drawable.achievement_locked)
                name.text = "???"
                name.setTextColor(Color.parseColor("#AAFFFFFF"))
                description.text = achievement.description
                description.setTextColor(Color.parseColor("#AAFFFFFF"))
            }
        }
    }
}