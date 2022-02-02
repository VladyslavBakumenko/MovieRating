package com.example.movierating.presentation.ui.login_activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import com.example.movierating.R
import com.example.movierating.presentation.ui.main_activity.MainActivity
import com.example.movierating.presentation.ui.regestration_activity.RegistrationActivity
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    private lateinit var tilEMail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEMail: EditText
    private lateinit var etPassword: EditText
    private lateinit var registrationButton: Button
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        addTextChangeListeners()
        observeViewModel()


        registrationButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            viewModel.checkUserInDatabase(
                etEMail.text.toString(),
                etPassword.text.toString()
            )
        }

    }

    private fun addTextChangeListeners() {
        etEMail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputEMail()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputPassword()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun observeViewModel() {
        viewModel.errorInputEMail.observe(this) {
            val message = if (it) {
                resources.getString(R.string.invalid_eMail)
            } else {
                null
            }
            tilEMail.error = message
        }
        viewModel.errorInputPassword.observe(this) {
            val message = if (it) {
                resources.getString(R.string.invalid_password)
            } else {
                null
            }
            tilPassword.error = message
        }

        viewModel.userFound.observe(this) {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(USER_LOGIN_ACTIVITY, etEMail.text.toString())
                startActivity(intent)
            } else {
                val toast = Toast.makeText(
                    this,
                    resources.getString(R.string.login_error),
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }

    }


    private fun initViews() {
        tilEMail = findViewById(R.id.tilEMail)
        tilPassword = findViewById(R.id.tilPassword)
        etEMail = findViewById(R.id.etEMail)
        etPassword = findViewById(R.id.etPassword)
        registrationButton = findViewById(R.id.registrationButton)
        loginButton = findViewById(R.id.loginButton)
    }

    companion object {
        const val USER_LOGIN_ACTIVITY = "userLoginActivity"
    }
}