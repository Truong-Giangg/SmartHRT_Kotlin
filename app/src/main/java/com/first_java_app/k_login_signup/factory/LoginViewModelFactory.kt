package com.first_java_app.k_login_signup.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.first_java_app.k_login_signup.MyApp
import com.first_java_app.k_login_signup.viewModel_Adapter.LoginViewModel
import com.first_java_app.k_login_signup.viewModel_Adapter.SignUpProfileViewModel

class LoginViewModelFactory(val app : MyApp) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(app.prefs) as T
        }
        throw IllegalArgumentException("unknown view model")
    }
}