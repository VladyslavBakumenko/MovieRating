package com.example.movierating.presentation.ui.regestration_activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.movierating.R
import com.example.movierating.databinding.ActivityRegestrationBinding
import com.example.movierating.presentation.ui.main_activity.MainActivity
import com.google.android.material.textfield.TextInputLayout

class RegistrationActivity : AppCompatActivity() {

    private lateinit var viewModel: RegistrationViewModel
    private lateinit var binding: ActivityRegestrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegestrationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
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
            val message = if (it) {
                resources.getString(R.string.invalid_eMail)
            } else {
                null
            }
           binding.tilEMailRegistrationActivity.error = message
        }
        viewModel.errorInputPassword.observe(this) {
            val message = if (it) {
                resources.getString(R.string.invalid_password)
            } else {
                null
            }
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
                intent.putExtra(USER_REGISTRATION_ACTIVITY, binding.etEMailRegistrationActivity.text.toString())
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