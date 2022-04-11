package com.example.movierating.presentation.ui.fragments.contactsFragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.movierating.R
import com.example.movierating.data.ContactInfo
import com.example.movierating.data.MyForegroundService
import com.example.movierating.data.MyWorker
import com.example.movierating.databinding.FragmentContactsBinding
import com.example.movierating.presentation.ui.activitys.mainActivity.MainActivity
import com.example.movierating.presentation.ui.recyclerViews.cuntactsRv.ContactsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


@AndroidEntryPoint
class ContactsFragment : Fragment() {

    private var binding: FragmentContactsBinding? = null
    private val viewModel: ContactsFragmentsViewModel by viewModels()
    private val contactList = mutableListOf<ContactInfo>()

    @Inject
    lateinit var contactsListAdapter: ContactsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        viewModel.checkContactsPermission()
        setContactListener()

        viewModel.permissionGranted.observe(viewLifecycleOwner, Observer {
            if (it) {
                starsService()
            } else getPermission()
        })
    }

    private fun getPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.WRITE_CONTACTS
            ), MainActivity.CONTACTS_RC
        )
    }

    private fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        binding?.movieRecyclerView?.layoutManager = linearLayoutManager
        binding?.movieRecyclerView?.adapter = contactsListAdapter
       // contactsListAdapter.contactsList = contactList
    }

    private fun setContactListener() {
        contactsListAdapter.onContactClickListener =
            object : ContactsListAdapter.OnContactClickListener {
                override fun onMovieClick(contactInfo: ContactInfo) {

                    requireActivity().supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(
                            R.id.fragmentContainerView,
                            EditContactFragment.newInstance(contactInfo)
                        )
                        .commit()
                }
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == MainActivity.CONTACTS_RC) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                starsService()
            }
        }
    }

    private fun starsService() {
        ContextCompat.startForegroundService(
            requireContext(),
            MyForegroundService.newIntentForGetContacts(
                requireContext()
            )
        )
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onEvent(contactInfo: ContactInfo) {
        if(contactList.size == 0) setUpRecyclerView()
        contactList.add(contactInfo)
        contactsListAdapter.contactsList = contactList
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    companion object {
        fun newInstance(): ContactsFragment = ContactsFragment()
    }
}