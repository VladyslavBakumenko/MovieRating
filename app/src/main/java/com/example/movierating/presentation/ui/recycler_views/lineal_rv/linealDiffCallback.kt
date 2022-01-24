package com.example.movierating.presentation.ui.recycler_views.lineal_rv

import androidx.recyclerview.widget.DiffUtil
import com.example.movierating.data.internet.MovieResult

class linealDiffCallback: DiffUtil.ItemCallback<MovieResult>() {
    override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return oldItem == newItem
    }
}