package com.example.movierating.presentation.ui.recyclerViews.cuntactsRv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.data.ContactInfo
import com.example.movierating.databinding.ContactItemBinding

class ContactItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ContactItemBinding.bind(view)

    private val contactName = binding.contactName
    private val contactNumber = binding.contactNumber
    private val contactCardView = binding.contactCardView

    fun bind(
        contactInfo: ContactInfo,
        onContactClickListener: ContactsListAdapter.OnContactClickListener?
    ) {
        contactName.text = contactInfo.name
        contactNumber.text = contactInfo.number

        contactCardView.setOnClickListener {
            onContactClickListener?.onMovieClick(contactInfo)
        }
    }
}