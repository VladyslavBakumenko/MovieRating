package com.example.movierating.presentation.ui.activitys.registrationActivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.movierating.R
import com.example.movierating.databinding.ActivityRegestrationBinding
import com.example.movierating.presentation.ui.activitys.mainActivity.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {

    private val viewModel: RegistrationViewModel by viewModels()
    private lateinit var binding: ActivityRegestrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegestrationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        addTextChangeListeners()
        observeViewModel()

        binding.registrationButtonRegistrationActivity.setOnClickListener {

            viewModel.addUserToData(
                binding.etEMailRegistrationActivity.text.toString(),
                binding.etPasswordRegistrationActivity.text.toString(),
            )
        }
    }


    private fun addTextChangeListeners() {
        binding.etEMailRegistrationActivity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputEMail()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.etPasswordRegistrationActivity.addTextChangedListener(object : TextWatcher {
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
            binding.tilEMailRegistrationActivity.error = message
        }
        viewModel.errorInputPassword.observe(this) {
            val message = if (it) resources.getString(R.string.invalid_password)
            else null
            binding.tilPasswordRegistrationActivity.error = message
        }

        viewModel.userAddedSuccessfully.observe(this) {
            if (it) {
                val toast = Toast.makeText(
                    this,
                    "Користувач успішно зареєстрований",
                    Toast.LENGTH_SHORT
                )
                toast.show()

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(
                    USER_REGISTRATION_ACTIVITY,
                    binding.etEMailRegistrationActivity.text.toString()
                )
                startActivity(intent)
            } else {
                val toast = Toast.makeText(
                    this,
                    "Користувач з таким eMail уже зареєстрований",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
    }

    companion object {
        const val USER_REGISTRATION_ACTIVITY = "userRegistrationActivity"
    }
}