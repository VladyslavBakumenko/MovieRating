package com.example.movierating.presentation.ui.recyclerViews.cuntactsRv

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.data.ContactInfo
import com.example.movierating.data.internet.requestResults.moviesRequestResult.MovieResult
import com.example.movierating.presentation.ui.recyclerViews.linealRv.MovieListLinealAdapter

class ContactsListAdapter: RecyclerView.Adapter<ContactItemViewHolder>() {

    var contactsList = listOf<ContactInfo>()

    var onContactClickListener: OnContactClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.contact_item,
            parent,
            false
        )

        return ContactItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactItemViewHolder, position: Int) {
        holder.bind(contactsList[position], onContactClickListener)
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    interface OnContactClickListener {
        fun onMovieClick(contactInfo: ContactInfo)
    }
}