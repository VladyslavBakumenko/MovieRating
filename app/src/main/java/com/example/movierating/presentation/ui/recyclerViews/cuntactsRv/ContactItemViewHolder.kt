package com.example.movierating.presentation.ui.recyclerViews.cuntactsRv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.movierating.databinding.ContactItemBinding

class ContactItemViewHolder(view: View)  : RecyclerView.ViewHolder(view) {

    private val binding = ContactItemBinding.bind(view)

    private val contactName = binding.contactName
    private val contactNumber = binding.contactNumber

    fun bind() {

    }
}