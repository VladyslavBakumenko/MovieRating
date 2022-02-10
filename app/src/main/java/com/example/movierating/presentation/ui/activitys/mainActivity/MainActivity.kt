package com.example.movierating.presentation.ui.activitys.mainActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.movierating.R
import com.example.movierating.data.internet.ApiFactory
import com.example.movierating.databinding.ActivityMainBinding
import com.example.movierating.presentation.ui.activitys.loginActivity.LoginActivity
import com.example.movierating.presentation.ui.fragments.ProfileFragment
import com.example.movierating.presentation.ui.fragments.moviesFragment.MoviesFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        launchMoviesFragment()

        binding.navigationView.itemIconTintList
        binding.navigationView.setNavigationItemSelectedListener(this)

        val coroutineScope = CoroutineScope(Dispatchers.IO)


        coroutineScope.launch {
            val token = ApiFactory.movieApi.getRequestToken()
            Log.d("gfhgfgghhgdg", token.body()?.requestToken.toString())

           val result = ApiFactory.movieApi.authenticate(token.body()?.requestToken.toString())


            Log.d("gfhgfgghhgdg", result.body()?.sessionId.toString())


        }

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
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        return result
    }

    companion object {
        const val MOVIE_RESULT = "movie_result"
    }
}
