package com.example.movierating.presentation.ui.activitys.mainActivity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.movierating.R
import com.example.movierating.databinding.ActivityMainBinding
import com.example.movierating.presentation.ui.activitys.loginActivity.LoginActivity
import com.example.movierating.presentation.ui.fragments.ProfileFragment
import com.example.movierating.presentation.ui.fragments.moviesFragment.MoviesFragment
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
        observeViewModel()
        launchMoviesFragment()
        binding.navigationView.itemIconTintList
        binding.navigationView.setNavigationItemSelectedListener(this)

    }


    private fun launchMoviesFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, MoviesFragment.newInstance())
            .commit()
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
        if (id == R.id.profile) {
            result = true
            binding.drawerLayout.closeDrawer(Gravity.LEFT, true)
            launchProfileFragment()
        }
        if (id == R.id.exit) {
            result = true

            viewModel.unLoginUser()
            viewModel.unLoginSuccess.observe(this) {
                if (it) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        return result
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val drawer = binding.drawerLayout

        if (item.itemId == android.R.id.home) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START, true)
            } else drawer.openDrawer(GravityCompat.START, true)
        }
        return true
    }

    override fun onBackPressed() {
        binding.drawerLayout.closeDrawer(GravityCompat.START, true)
    }

    companion object {
        const val MOVIE_RESULT = "movie_result"
    }
}
