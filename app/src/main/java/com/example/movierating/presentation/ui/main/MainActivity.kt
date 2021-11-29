package com.example.movierating.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.movierating.R
import com.example.movierating.presentation.ui.ProfileFragment
import com.example.movierating.presentation.ui.details_fragment.DetailsFragment
import com.example.movierating.presentation.ui.lineal_fragment.LinealFragment
import com.example.movierating.presentation.ui.login.LoginActivity
import com.example.movierating.presentation.ui.recycler_views.MovieListTableAdapter
import com.example.movierating.presentation.ui.regestration.RegistrationActivity
import com.example.movierating.presentation.ui.table_fragment.TabelFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var movieListTableAdapter: MovieListTableAdapter
    private lateinit var changeFragmentButton: Button
    private lateinit var profileFragmentButton: Button
    private lateinit var backToLoginButton: Button
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


        navigationView.back_to_login_button.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        profileFragmentButton.setOnClickListener {
            if(savedInstanceState == null) {
                launchProfileFragment(profileFragment)
            }
        }

        backToLoginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
       }

        changeFragmentButton.setOnClickListener {
            launchRightFragment(linealFragment, tableFragment)
        }
    }

    private fun startFirstFragment(linealFragment: LinealFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, linealFragment)
            .commit()
        viewModel.changeModeToTabel()
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
        changeFragmentButton = findViewById(R.id.change_fragment_button)
        movieListTableAdapter = MovieListTableAdapter()
        profileFragmentButton = findViewById(R.id.profile_fragment_button)
        backToLoginButton = findViewById(R.id.back_to_login_button)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        navigationView.itemIconTintList
       // start = findViewById(R.id.open_navigation_view_button)
    }

    private fun initFragments() {
        linealFragment = LinealFragment()
        tableFragment = TabelFragment()
        detailsFragment = DetailsFragment()
        profileFragment = ProfileFragment()
    }

    private fun launchRightFragment(linealFragment: LinealFragment, tabelFragment: TabelFragment,) {

        when (viewModel.fragmentStatus.value) {
            LINEAL_MODE -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, linealFragment)
                    .commit()
                viewModel.changeModeToTabel()
            }

            TABEL_MODE -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, tabelFragment)
                    .commit()
                viewModel.changeModeToLineal()
            }
            else -> throw RuntimeException("Unknown fragment mode")
        }
    }

    companion object {
        const val LINEAL_MODE = false
        const val TABEL_MODE = true
    }
}
