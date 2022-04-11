package com.example.movierating.presentation.ui.fragments.contactsFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.movierating.data.ContactInfo
import com.example.movierating.data.MyForegroundService
import com.example.movierating.databinding.FragmentEditContactBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditContactFragment : Fragment() {

    private var binding: FragmentEditContactBinding? = null
    private lateinit var contactInfo: ContactInfo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditContactBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgs()
        setFields()

        binding?.saveButton?.setOnClickListener {
            val contactName = binding?.etName?.text.toString()
            val contactNumber = binding?.etNumber?.text.toString()
            starsService(ContactInfo(contactName, contactNumber, contactInfo.id.toString()))
        }
    }

    private fun starsService(contactInfo: ContactInfo) {
        ContextCompat.startForegroundService(
            requireContext(),
            MyForegroundService.newIntentForEditContact(
                requireContext(),
                contactInfo
            )
        )
    }

    private fun setFields() {
        binding?.let {
            it.etName.setText(contactInfo.name)
            it.etNumber.setText(contactInfo.number)
        }
    }

    private fun parseArgs() {
        contactInfo = requireArguments().getParcelable<ContactInfo>(CONTACT_INFO) as ContactInfo
    }

    companion object {
        private const val CONTACT_INFO = "contact_info"

        fun newInstance(contactInfo: ContactInfo): EditContactFragment {
            val fragment = EditContactFragment()
            val args = Bundle()
            args.putParcelable(CONTACT_INFO, contactInfo)

            fragment.arguments = args

            return fragment
        }
    }
}