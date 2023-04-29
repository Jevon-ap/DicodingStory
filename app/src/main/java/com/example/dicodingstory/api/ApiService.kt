package com.example.dicodingstory.api

import com.example.dicodingstory.data.LoginRequest
import com.example.dicodingstory.data.LoginResponse
import com.example.dicodingstory.data.RegisterRequest
import com.example.dicodingstory.data.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>
}