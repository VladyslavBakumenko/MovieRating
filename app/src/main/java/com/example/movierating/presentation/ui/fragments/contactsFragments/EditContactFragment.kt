package com.example.movierating.presentation.ui.fragments.contactsFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.movierating.data.ContactInfo
import com.example.movierating.data.MyWorker
import com.example.movierating.databinding.FragmentEditContactBinding
import com.example.movierating.presentation.ui.fragments.moviesFragment.MoviesFragmentViewModel
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
            startService(contactName, contactNumber, contactInfo.id.toString())
        }
    }

    private fun startService(contactName: String, contactNumber: String, contactId: String) {

        val workManager = WorkManager.getInstance(requireContext())
        workManager.enqueueUniqueWork(
            MyWorker.WORK_NAME,
            ExistingWorkPolicy.KEEP,
            MyWorker.makeRequestEditContacts(contactName, contactNumber, contactId)
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