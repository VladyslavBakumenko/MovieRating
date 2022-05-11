package com.example.movierating.presentation.ui.recyclerViews.cuntactsRv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.R
import com.example.movierating.data.contactsAndContactsServices.ContactInfo

class ContactsListAdapter: RecyclerView.Adapter<ContactItemViewHolder>() {

    var contactsList = listOf<ContactInfo>()
    set(value) {
        field = value
        notifyItemInserted(contactsList.size)
    }

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