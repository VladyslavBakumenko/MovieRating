package com.example.movierating.presentation.ui.activitys.loginActivity


sealed class LoginActivityState()

class ErrorInputEMail(val value: Boolean): LoginActivityState()
class ErrorInputPassword(val value: Boolean): LoginActivityState()
class UserFound(val value: Boolean): LoginActivityState()
object NetworkError: LoginActivityState()