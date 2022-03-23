package com.example.movierating.presentation.ui.recyclerViews.cuntactsRv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R

class ContactsListAdapter: RecyclerView.Adapter<ContactItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.contact_item,
            parent,
            false
        )

        return ContactItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactItemViewHolder, position: Int) {
        TODO("Gfdfgd")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}