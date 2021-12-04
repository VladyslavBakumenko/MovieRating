package com.example.movierating.presentation.ui.main_activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.movierating.R
import com.example.movierating.presentation.ui.fragments.DetailsFragment
import com.example.movierating.presentation.ui.fragments.LinealFragment
import com.example.movierating.presentation.ui.fragments.ProfileFragment
import com.example.movierating.presentation.ui.fragments.TabelFragment
import com.example.movierating.presentation.ui.login_activity.LoginActivity
import com.example.movierating.presentation.ui.regestration_activity.RegistrationActivity
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private lateinit var linealFragment: LinealFragment
    private lateinit var tableFragment: TabelFragment
    private lateinit var detailsFragment: DetailsFragment
    private lateinit var profileFragment: ProfileFragment



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        initFragments()
        initViews()
        startFirstFragment(linealFragment)

        navigationView.itemIconTintList
        navigationView.setNavigationItemSelectedListener(this)
        viewModel.loadData()



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

    private fun initViews() {

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        navigationView.itemIconTintList
    }

    private fun initFragments() {
        linealFragment = LinealFragment()
        tableFragment = TabelFragment()
        detailsFragment = DetailsFragment()
        profileFragment = ProfileFragment()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        var result = false
        if (id == R.id.profile) {
            result = true
            drawerLayout.closeDrawer(Gravity.LEFT, true)
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
