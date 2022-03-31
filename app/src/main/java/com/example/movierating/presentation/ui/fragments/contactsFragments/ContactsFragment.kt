package com.example.movierating.presentation.ui.fragments.contactsFragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.movierating.R
import com.example.movierating.data.ContactInfo
import com.example.movierating.data.MyWorker
import com.example.movierating.databinding.FragmentContactsBinding
import com.example.movierating.presentation.ui.activitys.mainActivity.MainActivity
import com.example.movierating.presentation.ui.recyclerViews.cuntactsRv.ContactsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ContactsFragment : Fragment() {

    private var binding: FragmentContactsBinding? = null
    private val viewModel: ContactsFragmentsViewModel by viewModels()


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
        viewModel.checkContactsPermission()
        setContactListener()

        viewModel.permissionGranted.observe(viewLifecycleOwner, Observer {
            if (it) startService()
            else getPermission()
        })
    }

    private fun getPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.WRITE_CONTACTS
            ), MainActivity.PERMISSION_CODE
        )
    }

    private fun setUpRecyclerView(contactList: List<ContactInfo>) {

        val linearLayoutManager = LinearLayoutManager(context)
        binding?.movieRecyclerView?.layoutManager = linearLayoutManager
        binding?.movieRecyclerView?.adapter = contactsListAdapter
        contactsListAdapter.contactsList = contactList
    }


    private fun startService() {

        val workManager = WorkManager.getInstance(requireContext())
        workManager.enqueueUniqueWork(
            MyWorker.WORK_NAME,
            ExistingWorkPolicy.KEEP,
            MyWorker.makeRequest(null, null, null)
        )

        val oneTimeRequest = MyWorker.makeRequest("contacts_list", "contacts_list", "contacts_list")


        workManager.getWorkInfoByIdLiveData(oneTimeRequest.id)
            .observe(viewLifecycleOwner, Observer {
                //val contactList = it.outputData
                //setUpRecyclerView(contactList)
            })


/*        val test =  workManager.getWorkInfoById(oneTimeRequest.id).get().outputData.getString("contacts_list")
        Log.d("fdffd", test.toString())*/
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

        if (requestCode == MainActivity.PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                startService()
            }
        }
    }


    companion object {
        fun newInstance(): ContactsFragment = ContactsFragment()
    }
}