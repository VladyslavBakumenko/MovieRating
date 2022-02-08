package com.example.movierating.presentation.ui.activitys.mainActivity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.movierating.R
import com.example.movierating.databinding.ActivityMainBinding
import com.example.movierating.presentation.ui.activitys.loginActivity.LoginActivity
import com.example.movierating.presentation.ui.fragments.LinealFragment
import com.example.movierating.presentation.ui.fragments.ProfileFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        startFirstFragment(LinealFragment.newInstance())

        binding.navigationView.itemIconTintList
        binding.navigationView.setNavigationItemSelectedListener(this)
        viewModel.loadData()
    }


    private fun startFirstFragment(linealFragment: LinealFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, linealFragment)
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

    companion object{
        const val MOVIE_RESULT = "movie_result"
    }
}
