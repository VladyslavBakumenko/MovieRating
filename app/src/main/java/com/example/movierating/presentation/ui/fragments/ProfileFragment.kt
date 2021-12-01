package com.example.movierating.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.movierating.R
import com.example.movierating.presentation.ui.login_activity.LoginActivity
import com.example.movierating.presentation.ui.regestration_activity.RegistrationActivity

class ProfileFragment : Fragment() {
    private lateinit var userTextView: TextView
    private lateinit var userFromLoginActivity: String
    private lateinit var userFromRegistrationActivity: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userFromLoginActivity = requireArguments()[LoginActivity.USER_LOGIN_ACTIVITY].toString()
        userFromRegistrationActivity =
            requireArguments()[RegistrationActivity.USER_REGISTRATION_ACTIVITY].toString()

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userTextView = view.findViewById(R.id.text_view_profile)

        if (userFromLoginActivity == "null") {
            userTextView.text = userFromRegistrationActivity
        } else {
            userTextView.text = userFromLoginActivity
        }
    }
}