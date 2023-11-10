package com.chaimaerazzouki.quizgame.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.chaimaerazzouki.quizgame.R
import com.chaimaerazzouki.quizgame.model.ThemesItem
import com.chaimaerazzouki.quizgame.view.listeners.OnItemClickListener
import com.chaimaerazzouki.quizgame.viewmodel.QuizViewModel
import com.chaimaerazzouki.quizgame.viewmodel.ThemeSelectionViewModel

class ThemesAdapter(
    private val themesItems: List<ThemesItem>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ThemesAdapter.ThemesViewHolder>() {

    inner class ThemesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define views from themes_item.xml and bind data here
        val themeItemCardView : CardView = itemView.findViewById(R.id.themeItemCardView)
        val themeNameTextView: TextView = itemView.findViewById(R.id.nameThemesItem)
        val themeImageView: ImageView = itemView.findViewById(R.id.imageThemesItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.themes_item, parent, false)
        return ThemesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ThemesViewHolder, position: Int) {
        val currentItem = themesItems[position]

        holder.themeNameTextView.text = currentItem.name
        holder.themeImageView.setImageResource(currentItem.imageResourceId)

        holder.themeItemCardView.setOnClickListener{
            val theme = holder.themeNameTextView.text.toString()
            onItemClickListener.onItemClick(theme)
        }
    }

    override fun getItemCount(): Int {
        return themesItems.size
    }
}