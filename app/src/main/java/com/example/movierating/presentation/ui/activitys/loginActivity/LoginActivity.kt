package com.example.movierating.presentation.ui.activitys.loginActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.movierating.R
import com.example.movierating.databinding.ActivityLoginBinding
import com.example.movierating.presentation.ui.activitys.mainActivity.MainActivity
import com.example.movierating.presentation.ui.activitys.registrationActivity.RegistrationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startMainActivityIfUserLogin()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addTextChangeListeners()
        observeViewModel()

        binding.registrationButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            viewModel.loginUser(
                binding.etUserName.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }

    private fun addTextChangeListeners() {
        binding.etUserName.doOnTextChanged { text, start, before, count ->
            viewModel.resetErrorInputEMail()
        }
        binding.etPassword.doOnTextChanged { text, start, before, count ->
            viewModel.resetErrorInputPassword()
        }
    }

    private fun observeViewModel() {
        viewModel.errorInputEMail.observe(this) {
            val message = if (it) resources.getString(R.string.invalid_user_name)
            else null
            binding.tilUserName.error = message
        }
        viewModel.errorInputPassword.observe(this) {
            val message = if (it) resources.getString(R.string.invalid_password)
            else null
            binding.tilPassword.error = message
        }

        viewModel.userFound.observe(this) {
            if (it) {
                startMainActivity()
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

    private fun startMainActivityIfUserLogin() {
        if (viewModel.checkUserToLogin()) {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }


    companion object {
        const val USER_LOGIN_ACTIVITY = "userLoginActivity"
    }
}