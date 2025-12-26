package com.example.et_note.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.et_note.data.remote.ApiService
import com.example.et_note.data.remote.HttpClientFactory
import com.example.et_note.model.AuthRequest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {
    private val _email = MutableStateFlow<String>("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow<String>("")
    val password = _password.asStateFlow()


    private val apiService = ApiService(HttpClientFactory.getHttpClient())
    private val _uiState = MutableStateFlow<AuthState>(AuthState.Normal)
    val uiState = _uiState.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<AuthNavigation>()
    val navigationFlow = _navigationFlow.asSharedFlow()

    fun onErrorClick(){

        viewModelScope.launch {
            _uiState.value = AuthState.Normal
        }
    }

    fun onSuccessClick(email: String){

        viewModelScope.launch {
            _navigationFlow.emit(AuthNavigation.NavigateToHome(email))
        }
    }

    fun onEmailUpdate(email: String){
        _email.value = email
    }

    fun onPasswordUpdate(password: String){
        _password.value = password
    }


    fun signIn(){
        viewModelScope.launch {
            val request = AuthRequest(email.value, password.value)
            _uiState.value = AuthState.Loading
            val result = apiService.login(request)
            if(result.isSuccess) _uiState.value = AuthState.Success(result.getOrNull()!!)
            else AuthState.Failure(result.exceptionOrNull()?.message.toString())
        }
    }
}