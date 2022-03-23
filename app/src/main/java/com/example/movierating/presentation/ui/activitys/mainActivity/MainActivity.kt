package com.example.movierating.presentation.ui.activitys.mainActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.example.movierating.R
import com.example.movierating.data.ContactInfo
import com.example.movierating.databinding.ActivityMainBinding
import com.example.movierating.presentation.ui.activitys.loginActivity.LoginActivity
import com.example.movierating.presentation.ui.fragments.ProfileFragment
import com.example.movierating.utils.createToast
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_dialog_dialer);

        observeViewModel()

        binding.navigationView.itemIconTintList
        binding.navigationView.setNavigationItemSelectedListener(this)

    }

    private fun checkContactPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_CONTACTS), READ_CONTACTS_CODE
            )
        } else {
            Log.d("fdfdfd", "${getContacts()}")
            getContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == READ_CONTACTS_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContacts()
            }
        }
    }

    private fun launchProfileFragment() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainerView, ProfileFragment.newInstance(intent))
            .commit()

    }

    private fun observeViewModel() {
        viewModel.networkError.observe(this) {
            if (it) createToast(resources.getString(R.string.something_went_wrong))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        var result = false

        when (id) {
            R.id.profile -> {
                result = true
                binding.drawerLayout.closeDrawer(Gravity.LEFT, true)
                launchProfileFragment()
            }

            R.id.exit -> {
                result = true

                viewModel.unLoginUser()
                viewModel.unLoginSuccess.observe(this) {
                    if (it) {
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }
            }

            R.id.getContacts -> {
                Log.d("fdfdfdf", "tesst")
                checkContactPermission()
            }
        }


        return result
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val drawer = binding.drawerLayout

        if (item.itemId == android.R.id.home) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_dialog_dialer);
                drawer.closeDrawer(GravityCompat.START, true)
            } else {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_revert);
                drawer.openDrawer(GravityCompat.START, true)
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START, true)
        else super.onBackPressed()
    }

    @SuppressLint("Range")
    private fun getContacts(): String? {
        val contacts = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null,
            null
        )

        val contactList: MutableList<ContactInfo> = ArrayList()

        contacts?.let {
            while (it.moveToNext()) {

                val name = contacts.getString(
                    contacts.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    )
                )

                val number =
                    contacts.
                    getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                val contact = ContactInfo(name, number)
                contactList.add(contact)
            }
        }

        return null
    }

    companion object {
        const val MOVIE_RESULT = "movie_result"
        const val READ_CONTACTS_CODE = 1
    }
}
