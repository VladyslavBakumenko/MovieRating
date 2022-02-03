package com.example.movierating.presentation.ui.loginActivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.movierating.R
import com.example.movierating.databinding.ActivityLoginBinding
import com.example.movierating.presentation.ui.mainActivity.MainActivity
import com.example.movierating.presentation.ui.registrationActivity.RegistrationActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        initViews()
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        addTextChangeListeners()
        observeViewModel()


        binding.registrationButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            viewModel.checkUserInDatabase(
                binding.etEMail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

    }

    private fun addTextChangeListeners() {
        binding.etEMail.addTextChangedListener(object : TextWatcher {
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
            val message = if (it) resources.getString(R.string.invalid_eMail)
            else null
            binding.tilEMail.error = message
        }
        viewModel.errorInputPassword.observe(this) {
            val message = if (it) resources.getString(R.string.invalid_password)
            else null
            binding.tilPassword.error = message
        }

        viewModel.userFound.observe(this) {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(USER_LOGIN_ACTIVITY, binding.etEMail.text.toString())
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

    }

    companion object {
        const val USER_LOGIN_ACTIVITY = "userLoginActivity"
    }
}