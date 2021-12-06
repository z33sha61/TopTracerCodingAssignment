package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class LoginState {

    object Idle : LoginState()
    class Success(val username: String) : LoginState()
    object UsernameError : LoginState()
    object PasswordError : LoginState()

}

class LoginViewModel : ViewModel() {

    val loginState = MutableLiveData<LoginState>(LoginState.Idle)

    fun login(username: String, password: String) {
        if (username.isNotEmpty()
        ) {
            if (password == "password") {
                loginState.postValue(LoginState.Success(username))
            } else {
                loginState.postValue(LoginState.PasswordError)
            }
        } else {
            loginState.postValue(LoginState.UsernameError)
        }
    }

}