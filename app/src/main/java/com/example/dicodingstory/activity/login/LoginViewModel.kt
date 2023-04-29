package com.example.dicodingstory.activity.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingstory.api.RetrofitClient
import com.example.dicodingstory.data.LoginRequest
import com.example.dicodingstory.data.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel: ViewModel() {
    val loginResponse = MutableLiveData<Response<LoginResponse>>()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val response = RetrofitClient.apiInstance.loginUser(LoginRequest(email, password))
            loginResponse.postValue(response)
        }
    }
}