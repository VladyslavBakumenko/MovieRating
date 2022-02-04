package com.example.movierating.presentation.ui.activitys.mainActivity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.movierating.R
import com.example.movierating.databinding.ActivityMainBinding
import com.example.movierating.presentation.ui.fragments.DetailsFragment
import com.example.movierating.presentation.ui.fragments.LinealFragment
import com.example.movierating.presentation.ui.fragments.ProfileFragment
import com.example.movierating.presentation.ui.fragments.TableFragment
import com.example.movierating.presentation.ui.activitys.loginActivity.LoginActivity
import com.example.movierating.presentation.ui.activitys.registrationActivity.RegistrationActivity
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


    private lateinit var linealFragment: LinealFragment
    private lateinit var tableFragment: TableFragment
    private lateinit var detailsFragment: DetailsFragment
    private lateinit var profileFragment: ProfileFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initFragments()
        startFirstFragment(linealFragment)

        binding.navigationView.itemIconTintList
        binding.navigationView.setNavigationItemSelectedListener(this)
        viewModel.loadData()
    }

    private fun initFragments() {
        linealFragment = LinealFragment()
        tableFragment = TableFragment()
        detailsFragment = DetailsFragment()
        profileFragment = ProfileFragment()
    }

    private fun startFirstFragment(linealFragment: LinealFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, linealFragment)
            .commit()
    }

    private fun launchProfileFragment(profileFragment: ProfileFragment) {
        val userFromLoginActivity: String =
            intent.getStringExtra(LoginActivity.USER_LOGIN_ACTIVITY).toString()

        val userFromRegistrationActivity: String =
            intent.getStringExtra(RegistrationActivity.USER_REGISTRATION_ACTIVITY).toString()

        val args = Bundle()
        args.putString(LoginActivity.USER_LOGIN_ACTIVITY, userFromLoginActivity)
        args.putString(
            RegistrationActivity.USER_REGISTRATION_ACTIVITY,
            userFromRegistrationActivity
        )
        profileFragment.arguments = args

        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainerView, profileFragment)
            .commit()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        var result = false
        if (id == R.id.profile) {
            result = true
            binding.drawerLayout.closeDrawer(Gravity.LEFT, true)
            launchProfileFragment(profileFragment)
        }
        if (id == R.id.exit) {
            result = true
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        return result
    }
}
