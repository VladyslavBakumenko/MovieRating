package com.example.movierating.presentation.ui.fragments

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movierating.R
import com.example.movierating.data.ContactInfo
import com.example.movierating.databinding.FragmentContactsBinding
import com.example.movierating.presentation.ui.activitys.mainActivity.MainActivity
import com.example.movierating.presentation.ui.recyclerViews.cuntactsRv.ContactsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ContactsFragment : Fragment() {

    private var binding: FragmentContactsBinding? = null

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
        checkContactPermission()
        setContactListener()

    }

    private fun setUpRecyclerView() {
        val contactsList = getContacts()

        val linearLayoutManager = LinearLayoutManager(context)
        binding?.movieRecyclerView?.layoutManager = linearLayoutManager
        binding?.movieRecyclerView?.adapter = contactsListAdapter
        contactsListAdapter.contactsList = contactsList
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



    private fun checkContactPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.READ_CONTACTS), MainActivity.PERMISSION_CODE
            )
        } else {
            setUpRecyclerView()
            // startService()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == MainActivity.PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpRecyclerView()
                //  startService()
            }
        }
    }


    @SuppressLint("Range")
    private fun getContacts(): List<ContactInfo> {
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val cursor =
            requireContext().contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                null,
                null,
                null
            )

        val contactsList = mutableListOf<ContactInfo>()

        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {

                val id =
                    cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
                    )

                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val mobileNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))


                contactsList.add(ContactInfo(name, mobileNumber, id))
            }
            cursor.close()
        }
        return contactsList
    }

    companion object {
        fun newInstance(): ContactsFragment = ContactsFragment()
    }
}