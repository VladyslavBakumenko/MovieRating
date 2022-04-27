package com.example.movierating.presentation.ui.activitys.mainActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.example.movierating.R
import com.example.movierating.data.MyForegroundService
import com.example.movierating.databinding.ActivityMainBinding
import com.example.movierating.presentation.ui.activitys.loginActivity.LoginActivity
import com.example.movierating.presentation.ui.fragments.contactsFragments.ContactsFragment
import com.example.movierating.presentation.ui.fragments.ProfileFragment
import com.example.movierating.utils.createToast
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


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
        contentProviderTest()
    }

    private fun contentProviderTest() {
        contentResolver.query(
            Uri.parse("content://com.example.movierating/movies") ,
            null,
            null,
            null,
            null,
            null,
        )
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
                binding.drawerLayout.closeDrawer(Gravity.LEFT, true)
                supportFragmentManager.beginTransaction()

                    .replace(R.id.fragmentContainerView, ContactsFragment.newInstance())
                    .commit()
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



    companion object {
        const val MOVIE_RESULT = "movie_result"
        const val CONTACTS_RC = 1

    }
}
