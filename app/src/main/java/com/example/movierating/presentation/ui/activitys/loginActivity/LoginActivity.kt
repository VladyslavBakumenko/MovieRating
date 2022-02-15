package com.example.movierating.presentation.ui.activitys.loginActivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
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
        binding.etUserName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputEMail()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
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
            if (it == "null") {
                val toast = Toast.makeText(
                    this,
                    resources.getString(R.string.login_error),
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }


    companion object {
        const val USER_LOGIN_ACTIVITY = "userLoginActivity"
    }
}