package com.example.dicodingstory.activity.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.example.dicodingstory.api.ApiService
import com.example.dicodingstory.api.RetrofitClient
import com.example.dicodingstory.data.LoginResult
import com.example.dicodingstory.data.RegisterRequest
import com.example.dicodingstory.data.RegisterResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private val apiService = RetrofitClient.apiInstance

    val registerResponse: MutableLiveData<Response<RegisterResponse>> = MutableLiveData()

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            val response = apiService.registerUser(RegisterRequest(name, email, password))
            registerResponse.postValue(response)
            if (response.isSuccessful) {
                Log.d("REGISTER", "Registration successful: ${response.body()?.message}")
            } else {
                Log.d("REGISTER", "Registration failed: ${response.message()}")
            }
        }
    }
}