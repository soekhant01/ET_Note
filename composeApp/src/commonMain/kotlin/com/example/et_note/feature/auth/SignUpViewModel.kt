package com.example.et_note.feature.auth

import androidx.lifecycle.ViewModel
import com.example.et_note.model.AuthResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel: ViewModel() {
    private val _email = MutableStateFlow<String>("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow<String>("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow<String>("")
    val confirmPassword = _confirmPassword.asStateFlow()

    fun onEmailUpdate(email: String){
        _email.value = email
    }

    fun onPasswordUpdate(password: String){
        _password.value = password
    }

    fun onConfirmPasswordUpdate(confirmPassword: String){
        _confirmPassword.value = confirmPassword
    }

    fun signUp(){

    }
}

sealed class AuthState{
    object Normal: AuthState()
    object Loading: AuthState()
    class Success(val response: AuthResponse): AuthState()
    class Failure(val error: String): AuthState()
}