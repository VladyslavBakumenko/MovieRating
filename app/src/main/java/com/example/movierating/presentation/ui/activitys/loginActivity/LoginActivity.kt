package com.example.movierating.presentation.ui.activitys.loginActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.movierating.R
import com.example.movierating.databinding.ActivityLoginBinding
import com.example.movierating.presentation.ui.activitys.mainActivity.MainActivity
import com.example.movierating.utils.createToast
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

        binding.loginButton.setOnClickListener {
            viewModel.loginUser(
                binding.etUserName.text?.trim().toString(),
                binding.etPassword.text.toString()
            )
        }
    }

    private fun addTextChangeListeners() {
        binding.etUserName.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputEMail()
        }
        binding.etPassword.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputPassword()
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) {
            when(it){
                is ErrorInputEMail -> {
                    val message = if (it.value) resources.getString(R.string.invalid_user_name)
                    else null
                    binding.tilUserName.error = message
                }

                is ErrorInputPassword -> {
                    val message = if (it.value) resources.getString(R.string.invalid_password)
                    else null
                    binding.tilPassword.error = message
                }

                is UserFound -> {
                    if (it.value) {
                        startMainActivity()
                    } else createToast(resources.getString(R.string.login_error))
                }

                is NetworkError -> {
                    createToast(resources.getString(R.string.something_went_wrong))
                }
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