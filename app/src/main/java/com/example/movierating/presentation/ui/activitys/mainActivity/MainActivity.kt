package com.example.movierating.presentation.ui.activitys.mainActivity

import android.content.Intent
import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.movierating.R
import com.example.movierating.data.internet.requestResults.moviesRequestResult.MovieResult
import com.example.movierating.databinding.ActivityMainBinding
import com.example.movierating.presentation.ui.activitys.loginActivity.LoginActivity
import com.example.movierating.presentation.ui.fragments.DetailsFragment
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

    override fun onSaveInstanceState(outState: Bundle) {
        supportFragmentManager.fragments.forEachIndexed { index, fragment ->
            outState.putString(
                index.toString(), fragment.toString()
                    .replaceAfter("Fragment", "")
            )
        }
        outState.putInt(FRAGMENTS_IN_BACK_STACK, supportFragmentManager.fragments.size)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        var index = 0
        while (index < savedInstanceState.getInt(FRAGMENTS_IN_BACK_STACK)) {
            recoveryFragments(savedInstanceState.getString(index.toString()))
            index++
        }
    }

    private fun recoveryFragments(fragmentName: String?) {
        when (fragmentName) {
            MoviesFragment.MOVIE_FRAGMENT -> launchMoviesFragment()
            ProfileFragment.PROFILE_FRAGMENT -> launchProfileFragment()
            DetailsFragment.DETAILS_FRAGMENT -> {
                supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .add(
                        R.id.fragmentContainerView,
                        DetailsFragment.newInstance(MovieResult())
                    ).commit()
            }
        }
    }


    private fun launchMoviesFragment() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
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
                supportActionBar?.setIcon(R.drawable.ic_menu_camera)

                drawer.closeDrawer(GravityCompat.START, true)
            } else drawer.openDrawer(GravityCompat.START, true)
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

        const val FRAGMENTS_IN_BACK_STACK = "fragments_in_back_stack"
    }
}
